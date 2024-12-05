package pl.edu.pg.eti.kask.agent.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.edu.pg.eti.kask.agent.repository.api.AgentRepository;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class AgentPersistenceRepository implements AgentRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    @Override
    public Optional<Agent> find(UUID id) {
        return Optional.ofNullable(em.find(Agent.class, id));
    }

    @Override
    public List<Agent> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Agent> query = cb.createQuery(Agent.class);
        Root<Agent> root = query.from(Agent.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Agent entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Agent entity) {
        em.refresh(em.find(Agent.class, entity.getId()));
        em.remove(em.find(Agent.class, entity.getId()));
    }

    @Override
    public void update(Agent entity) {
        em.merge(entity);
    }

    @Override
    public Optional<Agent> findByLogin(String login) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Agent> query = cb.createQuery(Agent.class);
            Root<Agent> root = query.from(Agent.class);
            query.select(root);
            return Optional.of(em.createQuery(query).getSingleResult());

        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
