package pl.edu.pg.eti.kask.player.controller.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.player.dto.GetPlayerResponse;
import pl.edu.pg.eti.kask.player.dto.GetPlayersResponse;
import pl.edu.pg.eti.kask.player.dto.PatchPlayerRequest;
import pl.edu.pg.eti.kask.player.dto.PutPlayerRequest;

import java.util.UUID;

@Path("")
public interface PlayerController {
    @GET
    @Path("/players")
    @Produces(MediaType.APPLICATION_JSON)

    GetPlayersResponse getPlayers();

    @GET
    @Path("/teams/{id}/players")
    @Produces(MediaType.APPLICATION_JSON)
    GetPlayersResponse getTeamPlayers(@PathParam("id") UUID id);

    @GET
    @Path("/agents/{id}/players/")
    @Produces(MediaType.APPLICATION_JSON)
    GetPlayersResponse getAgentPlayers(@PathParam("id") UUID id);
    @GET
    @Path("/players/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetPlayerResponse getPlayer(@PathParam("id") UUID id);

    @GET
    @Path("/teams/{teamId}/players/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetPlayerResponse getTeamPlayer(@PathParam("teamId") UUID teamId, @PathParam("id") UUID id);

    @PUT
    @Path("/teams/{teamId}/players/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putPlayer(@PathParam("teamId") UUID teamId, @PathParam("id") UUID id, PutPlayerRequest request);

    @PATCH
    @Path("/teams/{teamId}/players/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchPlayer(@PathParam("teamId") UUID teamId, @PathParam("id") UUID id, PatchPlayerRequest request);


    @DELETE
    @Path("/players/{id}")
    void deletePlayer(@PathParam("id") UUID id);

    @DELETE
    @Path("/teams/{teamId}/players/{id}")
    void deletePlayerByTeam(@PathParam("teamId") UUID teamId, @PathParam("id") UUID id);
}
