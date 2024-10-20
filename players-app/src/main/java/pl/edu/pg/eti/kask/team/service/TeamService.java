package pl.edu.pg.eti.kask.team.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.team.entity.Team;
import pl.edu.pg.eti.kask.team.repository.api.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ApplicationScoped
@NoArgsConstructor(force = true)

public class TeamService {
    private final TeamRepository repository;
    
    @Inject
    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public Optional<Team> find(UUID id) {
        return repository.find(id);
    }
 
    public List<Team> findAll() {
        return repository.findAll();
    }

    public List<Team> findById() {
        return repository.findAll();
    }
    
    public void create(Team team) {
        repository.create(team);
    }

    public void delete(Team team) {
        repository.delete(team);
    }

}
