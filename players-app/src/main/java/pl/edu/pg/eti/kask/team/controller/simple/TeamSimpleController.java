package pl.edu.pg.eti.kask.team.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.team.controller.api.TeamController;
import pl.edu.pg.eti.kask.team.dto.GetTeamResponse;
import pl.edu.pg.eti.kask.team.dto.GetTeamsResponse;
import pl.edu.pg.eti.kask.team.service.TeamService;

import java.util.UUID;
@RequestScoped
public class TeamSimpleController implements TeamController {
    private final TeamService service;
    private final DtoFunctionFactory factory;

    @Inject
    public TeamSimpleController(TeamService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetTeamsResponse getTeams() {
        return factory.teamsToResponse().apply(service.findAll());
    }

    @Override
    public GetTeamResponse getTeam(UUID uuid) {
        return service.find(uuid)
                .map(factory.teamToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteTeam(UUID id) {
        service.find(id).ifPresentOrElse(
                service::delete,
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}