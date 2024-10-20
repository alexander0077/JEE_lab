package pl.edu.pg.eti.kask.datastore.component;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.serialization.component.CloningUtility;
import pl.edu.pg.eti.kask.team.entity.Team;
import pl.edu.pg.eti.kask.player.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    private final Set<Team> teams = new HashSet<>();
    private final Set<Agent> agents = new HashSet<>();
    private final Set<Player> players = new HashSet<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<Team> findAllTeams() {
        return teams.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createTeam(Team value) throws IllegalArgumentException {
        if (teams.stream().anyMatch(team -> team.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The team id \"%s\" is not unique".formatted(value.getId()));
        }
        teams.add(cloningUtility.clone(value));
    }

    public synchronized List<Player> findAllPlayers() {
        return players.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createPlayer(Player value) throws IllegalArgumentException {
        if (players.stream().anyMatch(player -> player.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The player id \"%s\" is not unique".formatted(value.getId()));
        }
        Player entity = cloneWithRelationships(value);
        players.add(entity);
    }

    public synchronized void updatePlayer(Player value) throws IllegalArgumentException {
        Player entity = cloneWithRelationships(value);
        if (players.removeIf(player -> player.getId().equals(value.getId()))) {
            players.add(entity);
        } else {
            throw new IllegalArgumentException("The player with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deletePlayer(UUID id) throws IllegalArgumentException {
        if (!players.removeIf(player -> player.getId().equals(id))) {
            throw new IllegalArgumentException("The player with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized List<Agent> findAllAgents() {
        return agents.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createAgent(Agent value) throws IllegalArgumentException {
        if (agents.stream().anyMatch(player -> player.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The agent id \"%s\" is not unique".formatted(value.getId()));
        }
        agents.add(cloningUtility.clone(value));
    }

    public synchronized void updateAgent(Agent value) throws IllegalArgumentException {
        if (agents.removeIf(player -> player.getId().equals(value.getId()))) {
            agents.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The agent with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteTeam(UUID id) throws IllegalArgumentException {
        this.findAllPlayers().stream()
                .filter(player -> player.getTeam().getId().equals(id))
                .forEach(player -> this.deletePlayer(player.getId()));

        if (!teams.removeIf(team -> team.getId().equals(id))) {
            throw new IllegalArgumentException("The team with id \"%s\" does not exist".formatted(id));
        }
    }

    private Player cloneWithRelationships(Player value) {
        Player entity = cloningUtility.clone(value);

        if (entity.getAgent() != null) {
            entity.setAgent(agents.stream()
                    .filter(user -> user.getId().equals(value.getAgent().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No agent with id \"%s\".".formatted(value.getAgent().getId()))));
        }

        if (entity.getTeam() != null) {
            entity.setTeam(teams.stream()
                    .filter(profession -> profession.getId().equals(value.getTeam().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No team with id \"%s\".".formatted(value.getTeam().getId()))));
        }

        return entity;
    }
}
