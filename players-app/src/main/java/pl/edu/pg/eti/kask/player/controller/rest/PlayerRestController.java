package pl.edu.pg.eti.kask.player.controller.rest;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.player.controller.api.PlayerController;
import pl.edu.pg.eti.kask.player.dto.GetPlayerResponse;
import pl.edu.pg.eti.kask.player.dto.GetPlayersResponse;
import pl.edu.pg.eti.kask.player.dto.PatchPlayerRequest;
import pl.edu.pg.eti.kask.player.dto.PutPlayerRequest;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.service.PlayerService;
import pl.edu.pg.eti.kask.team.entity.Team;
import pl.edu.pg.eti.kask.team.service.TeamService;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class PlayerRestController implements PlayerController {
    private PlayerService service;
    private final TeamService teamService;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public PlayerRestController(DtoFunctionFactory factory,
                                @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo,
                                TeamService teamService
    ) {
        this.factory = factory;
        this.uriInfo = uriInfo;
        this.teamService = teamService;
    }

    @EJB
    public void setService(PlayerService service) {
        this.service = service;
    }


    @Override
    public GetPlayersResponse getPlayers() {
        return factory.playersToResponse().apply(service.findAll());
    }

    @Override
    public GetPlayersResponse getTeamPlayers(UUID id) {
        return service.findAllByTeam(id)
                .map(factory.playersToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetPlayersResponse getAgentPlayers(UUID id) {
        return service.findAllByAgent(id)
                .map(factory.playersToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetPlayerResponse getPlayer(UUID id) {
        return service.find(id)
                .map(factory.playerToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetPlayerResponse getTeamPlayer(UUID teamId, UUID id) {
        if(teamService.find(teamId).isEmpty()) throw new NotFoundException();
        return service.find(id)
                .map(factory.playerToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @SneakyThrows
    public void putPlayer(UUID teamId, UUID id, PutPlayerRequest request) {
        try {
            Optional<Team> team = teamService.find(teamId);
            if(team.isEmpty()) throw new NotFoundException();
            Player newPlayer = factory.requestToPlayer().apply(id, request);
            newPlayer.setTeam(team.get());
            service.create(newPlayer);

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(PlayerController.class, "getPlayer")
                    .build(id)
                    .toString());

            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void patchPlayer(UUID teamId, UUID id, PatchPlayerRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> {
                    if(!entity.getTeam().getId().equals(teamId)) throw new NotFoundException();
                    service.update(factory.updatePlayer().apply(entity, request));
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deletePlayer(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deletePlayerByTeam(UUID teamId, UUID id) {
        if(teamService.find(teamId).isEmpty()) throw new NotFoundException();
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
