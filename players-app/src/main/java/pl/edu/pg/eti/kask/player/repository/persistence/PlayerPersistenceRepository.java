package pl.edu.pg.eti.kask.player.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.repository.api.PlayerRepository;
import pl.edu.pg.eti.kask.team.entity.Team;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
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
        return em.createQuery("select p from Player p", Player.class).getResultList();
    }

    @Override
    public void create(Player entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Player entity) {
        em.remove(em.find(Player.class, entity.getId()));
    }

    @Override
    public void update(Player entity) {
        em.merge(entity);
    }

    @Override
    public Optional<Player> findByIdAndAgent(UUID id, Agent agent) {
        try {
            return Optional.of(em.createQuery("select p from Player p where p.id = :id and p.agent = :agent", Player.class)
                    .setParameter("agent", agent)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }

    }

    @Override
    public List<Player> findAllByAgent(Agent agent) {
        return em.createQuery("select p from Player p where p.agent = :agent", Player.class)
                .setParameter("agent", agent)
                .getResultList();
    }

    @Override
    public List<Player> findAllByTeam(Team team) {
        return em.createQuery("select p from Player p where p.team = :team", Player.class)
                .setParameter("team", team)
                .getResultList();

    }
}
