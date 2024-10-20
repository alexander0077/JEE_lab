package pl.edu.pg.eti.kask.team.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.datastore.component.DataStore;
import pl.edu.pg.eti.kask.team.repository.api.TeamRepository;
import pl.edu.pg.eti.kask.team.entity.Team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class TeamInMemoryRepository implements TeamRepository {
    private final DataStore store;

    @Inject
    public TeamInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Team> find(UUID id) {
        return store.findAllTeams().stream()
                .filter(Team -> Team.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Team> findAll() {
        return store.findAllTeams();
    }

    @Override
    public void create(Team entity) {
        store.createTeam(entity);
    }

    @Override
    public void delete(Team team) {
        store.deleteTeam(team.getId());
    }

    @Override
    public void update(Team entity) {
        throw new UnsupportedOperationException("Operation not implemented.");
    }

}
