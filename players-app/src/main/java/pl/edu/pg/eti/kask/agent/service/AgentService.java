package pl.edu.pg.eti.kask.agent.service;

import pl.edu.pg.eti.kask.agent.repository.api.AgentRepository;
import pl.edu.pg.eti.kask.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.crypto.component.Pbkdf2PasswordHash;
import pl.edu.pg.eti.kask.agent.entity.Agent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class AgentService {

    private final AgentRepository repository;
    private final Pbkdf2PasswordHash passwordHash;
    private final Path portraitStorePath;

    public AgentService(AgentRepository repository, Pbkdf2PasswordHash passwordHash, Path portraitStorePath) {
        this.repository = repository;
        this.passwordHash = passwordHash;
        this.portraitStorePath = portraitStorePath;
    }

    public Optional<Agent> find(UUID id) {
        return repository.find(id);
    }

    public Optional<Agent> find(String login) {
        return repository.findByLogin(login);
    }

    public List<Agent> findAll() {
        return repository.findAll();
    }

    public void update(Agent agent) {
        repository.update(agent);
    }

    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }


    public void create(Agent agent) {
        agent.setPassword(passwordHash.generate(agent.getPassword().toCharArray()));
        repository.create(agent);
    }

    public boolean verify(String login, String password) {
        return find(login)
                .map(agent -> passwordHash.verify(password.toCharArray(), agent.getPassword()))
                .orElse(false);
    }

    public void updatePortrait(UUID id, InputStream is) {
        repository.find(id).ifPresent(agent -> {
            try {
                Path portraitPath = portraitStorePath.resolve(agent.getName() + ".png");
                Files.createDirectories(portraitStorePath);
                Files.copy(is, portraitPath, StandardCopyOption.REPLACE_EXISTING);
                agent.setPortrait(portraitPath.toString());
                repository.update(agent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public byte[] getAgentPortrait(UUID id) {
        return repository.find(id)
                .map(agent -> {
                    try {
                        return Files.readAllBytes(Paths.get(agent.getPortrait()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("No agent found"));
    }

    public void deletePortrait(UUID id) {
        repository.find(id).ifPresent(agent -> {
            try {
                Path portraitPath = Paths.get(agent.getPortrait());
                Files.delete(portraitPath);
                agent.setPortrait(null);
                repository.update(agent);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
