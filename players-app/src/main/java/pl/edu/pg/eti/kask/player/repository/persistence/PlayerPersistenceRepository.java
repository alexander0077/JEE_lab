package pl.edu.pg.eti.kask.player.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.entity.Player_;
import pl.edu.pg.eti.kask.player.repository.api.PlayerRepository;
import pl.edu.pg.eti.kask.team.entity.Team;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class PlayerPersistenceRepository implements PlayerRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Player> find(UUID id) {
        return Optional.ofNullable(em.find(Player.class, id));
    }

    @Override
    public List<Player> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Player> query = cb.createQuery(Player.class);
        Root<Player> root = query.from(Player.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Player entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Player entity) {
        em.refresh(em.find(Player.class, entity.getId()));
        em.remove(em.find(Player.class, entity.getId()));
    }

    @Override
    public void update(Player entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.merge(entity);
    }

    @Override
    public Optional<Player> findByIdAndAgent(UUID id, Agent agent) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Player> query = cb.createQuery(Player.class);
            Root<Player> root = query.from(Player.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Player_.agent), agent),
                            cb.equal(root.get(Player_.id), id)
                    ));
            return Optional.of(em.createQuery(query).getSingleResult());

        } catch (NoResultException ex) {
            return Optional.empty();
        }

    }

    @Override
    public List<Player> findAllByAgent(Agent agent) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Player> query = cb.createQuery(Player.class);
        Root<Player> root = query.from(Player.class);
        query.select(root)
                .where(cb.equal(root.get(Player_.agent), agent));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Player> findAllByTeam(Team team) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Player> query = cb.createQuery(Player.class);
        Root<Player> root = query.from(Player.class);
        query.select(root)
                .where(cb.equal(root.get(Player_.team), team));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Player> findAllByAgentAndByTeam(Agent agent, Team team) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Player> query = cb.createQuery(Player.class);
        Root<Player> root = query.from(Player.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Player_.agent), agent),
                        cb.equal(root.get(Player_.team), team)
                ));

        return em.createQuery(query).getResultList();
    }
}
