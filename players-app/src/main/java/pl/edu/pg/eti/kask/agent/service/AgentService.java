package pl.edu.pg.eti.kask.agent.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.agent.repository.api.AgentRepository;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.entity.AgentRoles;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class AgentService {

    private final AgentRepository repository;
    private final Pbkdf2PasswordHash passwordHash;
    private final Path portraitStorePath;

    @Inject
    public AgentService(AgentRepository repository,
                        @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.repository = repository;
        this.passwordHash = passwordHash;
        this.portraitStorePath = Path.of("portraitStore");
    }

    @RolesAllowed(AgentRoles.ADMIN)
    public Optional<Agent> find(UUID id) {
        return repository.find(id);
    }

    @RolesAllowed(AgentRoles.ADMIN)
    public Optional<Agent> find(String login) {
        return repository.findByLogin(login);
    }

    @RolesAllowed(AgentRoles.ADMIN)
    public List<Agent> findAll() {
        return repository.findAll();
    }


    @PermitAll
    public void update(Agent agent) {
        repository.update(agent);
    }

    @RolesAllowed(AgentRoles.ADMIN)
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    @PermitAll
    public void create(Agent agent) {
        agent.setPassword(passwordHash.generate(agent.getPassword().toCharArray()));
        repository.create(agent);
    }

    @PermitAll
    public boolean verify(String login, String password) {
        return find(login)
                .map(agent -> passwordHash.verify(password.toCharArray(), agent.getPassword()))
                .orElse(false);
    }

    @RolesAllowed(AgentRoles.ADMIN)
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

    @RolesAllowed(AgentRoles.ADMIN)
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

    @RolesAllowed(AgentRoles.ADMIN)
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
