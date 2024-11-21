package pl.edu.pg.eti.kask.team.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.team.entity.Team;
import pl.edu.pg.eti.kask.team.repository.api.TeamRepository;
import pl.edu.pg.eti.kask.agent.entity.AgentRoles;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@Log
public class TeamService {
    private final TeamRepository repository;
    
    @Inject
    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public Optional<Team> find(UUID id) {
        Optional<Team> team = repository.find(id);
        /* Until lazy loaded list of characters is not accessed it is not in cache, so it does not need bo te cared of. */
//        team.ifPresent(value -> log.info("Number of players: %d".formatted(value.getPlayers().size())));
        return team;

    }

    @PermitAll
    public List<Team> findAll() {
        return repository.findAll();
    }

    public List<Team> findById() {
        return repository.findAll();
    }

    @RolesAllowed(AgentRoles.ADMIN)
    public void create(Team team) {
        repository.create(team);
    }

    public void update(Team team) {
        repository.update(team);
    }

    @RolesAllowed(AgentRoles.ADMIN)
    public void delete(Team team) {
        repository.delete(team);
    }

}
