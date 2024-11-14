package pl.edu.pg.eti.kask.team.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.team.entity.Team;
import pl.edu.pg.eti.kask.team.repository.api.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ApplicationScoped
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
 
    public List<Team> findAll() {
        return repository.findAll();
    }

    public List<Team> findById() {
        return repository.findAll();
    }

    @Transactional
    public void create(Team team) {
        repository.create(team);
    }

    @Transactional
    public void update(Team team) {
        repository.update(team);
    }

    public void delete(Team team) {
        repository.delete(team);
    }

}
