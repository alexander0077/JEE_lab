package pl.edu.pg.eti.kask.agent.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.agent.repository.api.AgentRepository;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.datastore.component.DataStore;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class AgentInMemoryRepository implements AgentRepository {


    private final DataStore store;

    @Inject
    public AgentInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Agent> find(UUID id) {
        return store.findAllAgents().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Agent> findAll() {
        return store.findAllAgents();
    }

    @Override
    public void create(Agent entity) {
        store.createAgent(entity);
    }

    @Override
    public void delete(Agent entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void update(Agent entity) {
        store.updateAgent(entity);
    }

    @Override
    public Optional<Agent> findByLogin(String login) {
        return store.findAllAgents().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

}
