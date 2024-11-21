package pl.edu.pg.eti.kask.team.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.agent.entity.AgentRoles;
import pl.edu.pg.eti.kask.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.team.controller.api.TeamController;
import pl.edu.pg.eti.kask.team.dto.GetTeamResponse;
import pl.edu.pg.eti.kask.team.dto.GetTeamsResponse;
import pl.edu.pg.eti.kask.team.dto.PatchTeamRequest;
import pl.edu.pg.eti.kask.team.dto.PutTeamRequest;
import pl.edu.pg.eti.kask.team.service.TeamService;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
public class TeamRestController implements TeamController {
    private TeamService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public TeamRestController(DtoFunctionFactory factory,
                              @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(TeamService service) {
        this.service = service;
    }

    @Override
    public GetTeamsResponse getTeams() {
        try {
            return factory.teamsToResponse().apply(service.findAll());
        } catch (EJBAccessException ex) {
            throw new ForbiddenException(ex.getMessage());
        }
    }

    @Override
    public GetTeamResponse getTeam(UUID uuid) {
        return service.find(uuid)
                .map(factory.teamToResponse())
                .orElseThrow(NotFoundException::new);
    }


    @Override
    public void deleteTeam(UUID id) {
        try {
            service.find(id).ifPresentOrElse(
                    service::delete,
                    () -> {
                        throw new NotFoundException();
                    }
            );
        } catch (EJBAccessException ex) {
            throw new ForbiddenException(ex.getMessage());
        }
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
        } catch (EJBAccessException ex) {
            throw new ForbiddenException(ex.getMessage());
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