package pl.edu.pg.eti.kask.team.controller.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.player.controller.api.PlayerController;
import pl.edu.pg.eti.kask.player.dto.PatchPlayerRequest;
import pl.edu.pg.eti.kask.player.dto.PutPlayerRequest;
import pl.edu.pg.eti.kask.team.controller.api.TeamController;
import pl.edu.pg.eti.kask.team.dto.GetTeamResponse;
import pl.edu.pg.eti.kask.team.dto.GetTeamsResponse;
import pl.edu.pg.eti.kask.team.dto.PatchTeamRequest;
import pl.edu.pg.eti.kask.team.dto.PutTeamRequest;
import pl.edu.pg.eti.kask.team.service.TeamService;

import java.util.UUID;
@Path("")
public class TeamRestController implements TeamController {
    private final TeamService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public TeamRestController(TeamService service, DtoFunctionFactory factory,
                              @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
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

    @Override
    @SneakyThrows
    public void putTeam(UUID id, PutTeamRequest request) {
        try {
            service.create(factory.requestToTeam().apply(id, request));

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(TeamController.class, "getTeam")
                    .build(id)
                    .toString());

            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchTeam(UUID id, PatchTeamRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateTeam().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}