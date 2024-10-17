package pl.edu.pg.eti.kask.player.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.datastore.component.DataStore;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.repository.api.PlayerRepository;
import pl.edu.pg.eti.kask.team.entity.Team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class PlayerInMemoryRepository implements PlayerRepository {
    private final DataStore store;

    @Inject
    public PlayerInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Player> find(UUID id) {
        return store.findAllPlayers().stream()
                .filter(Player -> Player.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Player> findAll() {
        return store.findAllPlayers();
    }

    @Override
    public void create(Player entity) {
        store.createPlayer(entity);
    }

    @Override
    public void delete(Player entity) {
        store.deletePlayer(entity.getId());
    }

    @Override
    public void update(Player entity) {
        store.updatePlayer(entity);
    }

    @Override
    public Optional<Player> findByIdAndAgent(UUID id, Agent Agent) {
        return store.findAllPlayers().stream()
                .filter(Player -> Player.getAgent().equals(Agent))
                .filter(Player -> Player.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Player> findAllByAgent(Agent Agent) {
        return store.findAllPlayers().stream()
                .filter(Player -> Agent.equals(Player.getAgent()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Player> findAllByTeam(Team Team) {
        return store.findAllPlayers().stream()
                .filter(Player -> Team.equals(Player.getTeam()))
                .collect(Collectors.toList());
    }


}
