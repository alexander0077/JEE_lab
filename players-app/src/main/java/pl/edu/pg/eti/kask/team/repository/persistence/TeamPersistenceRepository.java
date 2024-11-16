package pl.edu.pg.eti.kask.team.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.team.repository.api.TeamRepository;
import pl.edu.pg.eti.kask.team.entity.Team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class TeamPersistenceRepository implements TeamRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Team> find(UUID id) {
        return Optional.ofNullable(em.find(Team.class, id));
    }

    @Override
    public List<Team> findAll() {
        return em.createQuery("select t from Team t", Team.class).getResultList();
    }

    @Override
    public void create(Team entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Team team) {
        /* Clearing cache used as workaround when not handling both sides of relationships, not recommended. */
//        em.getEntityManagerFactory().getCache().evictAll(); //Clearing 2nd level cache.
//        em.clear(); //Clearing 1st level cache.
        em.refresh(em.find(Team.class, team.getId()));
        em.remove(em.find(Team.class, team.getId()));

    }

    @Override
    public void update(Team entity) {
        em.merge(entity);
    }

}
