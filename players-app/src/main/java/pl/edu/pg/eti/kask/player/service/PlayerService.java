package pl.edu.pg.eti.kask.player.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.repository.api.AgentRepository;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.repository.api.PlayerRepository;
import pl.edu.pg.eti.kask.team.repository.api.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final AgentRepository agentRepository;

    @Inject
    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository, AgentRepository agentRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.agentRepository = agentRepository;
    }
    
    public Optional<Player> find(UUID id) {
        return playerRepository.find(id);
    }
    
    public Optional<Player> find(Agent agent, UUID id) {
        return playerRepository.findByIdAndAgent(id, agent);
    }
    
    public List<Player> findAll() {
        return playerRepository.findAll();
    }
    
    public List<Player> findAll(Agent agent) {
        return playerRepository.findAllByAgent(agent);
    }

    @Transactional
    public void create(Player player) {
        if (playerRepository.find(player.getId()).isPresent()) {
            throw new IllegalArgumentException("Player already exists.");
        }
        if (teamRepository.find(player.getTeam().getId()).isEmpty()) {
            throw new IllegalArgumentException("Teab does not exists.");
        }

        playerRepository.create(player);

        /* Both sides of relationship must be handled (if accessed) because of cache. */
//        professionRepository.find(character.getProfession().getId())
//                .ifPresent(profession -> profession.getCharacters().add(character));
//        userRepository.find(character.getUser().getId())
//                .ifPresent(user -> user.getCharacters().add(character));
    }

    @Transactional
    public void update(Player player) {
        playerRepository.update(player);
    }

    @Transactional
    public void delete(UUID id) {
        playerRepository.delete(playerRepository.find(id).orElseThrow());
    }

    public Optional<List<Player>> findAllByTeam(UUID id) {
        return teamRepository.find(id)
                .map(playerRepository::findAllByTeam);
    }

    public Optional<List<Player>> findAllByAgent(UUID id) {
        return agentRepository.find(id)
                .map(playerRepository::findAllByAgent);
    }

}
