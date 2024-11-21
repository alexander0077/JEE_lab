package pl.edu.pg.eti.kask.player.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.entity.AgentRoles;
import pl.edu.pg.eti.kask.agent.repository.api.AgentRepository;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.repository.api.PlayerRepository;
import pl.edu.pg.eti.kask.team.repository.api.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final AgentRepository agentRepository;
    private final SecurityContext securityContext;

    @Inject
    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository, AgentRepository agentRepository,
                         @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.agentRepository = agentRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed(AgentRoles.USER)
    public Optional<Player> find(UUID id) {
        return playerRepository.find(id);
    }

    @RolesAllowed(AgentRoles.USER)
    public Optional<Player> find(Agent agent, UUID id) {
        return playerRepository.findByIdAndAgent(id, agent);
    }

    @RolesAllowed(AgentRoles.USER)
    public Optional<Player> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(AgentRoles.ADMIN)) {
            return find(id);
        }
        Agent agent = agentRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(agent, id);
    }

    @RolesAllowed(AgentRoles.USER)
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @RolesAllowed(AgentRoles.USER)
    public List<Player> findAll(Agent agent) {
        return playerRepository.findAllByAgent(agent);
    }

    @RolesAllowed(AgentRoles.USER)
    public List<Player> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(AgentRoles.ADMIN)) {
            return findAll();
        }
        Agent agent = agentRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAll(agent);
    }

    @RolesAllowed(AgentRoles.ADMIN)
    public void create(Player player) {
        if (playerRepository.find(player.getId()).isPresent()) {
            throw new IllegalArgumentException("Player already exists.");
        }
        if (teamRepository.find(player.getTeam().getId()).isEmpty()) {
            throw new IllegalArgumentException("Teab does not exists.");
        }

        playerRepository.create(player);
    }

    @RolesAllowed(AgentRoles.USER)
    public void createForCallerPrincipal(Player player) {
        Agent agent = agentRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        player.setAgent(agent);
        create(player);
    }

    @RolesAllowed(AgentRoles.USER)
    public void update(Player player) {
        checkAdminRoleOrOwner(playerRepository.find(player.getId()));
        playerRepository.update(player);
    }

    @RolesAllowed(AgentRoles.USER)
    public void delete(UUID id) {
        checkAdminRoleOrOwner(playerRepository.find(id));
        playerRepository.delete(playerRepository.find(id).orElseThrow());
    }

    @RolesAllowed(AgentRoles.USER)
    public Optional<List<Player>> findAllByTeam(UUID id) {
        return teamRepository.find(id)
                .map(playerRepository::findAllByTeam);
    }

    @RolesAllowed(AgentRoles.USER)
    public Optional<List<Player>> findAllByAgent(UUID id) {
        return agentRepository.find(id)
                .map(playerRepository::findAllByAgent);
    }

    private void checkAdminRoleOrOwner(Optional<Player> player) throws EJBAccessException {
        if (securityContext.isCallerInRole(AgentRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(AgentRoles.USER)
                && player.isPresent()
                && player.get().getAgent().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }

}
