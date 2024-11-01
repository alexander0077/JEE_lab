package pl.edu.pg.eti.kask.team.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.team.dto.GetTeamResponse;
import pl.edu.pg.eti.kask.team.dto.GetTeamsResponse;
import pl.edu.pg.eti.kask.team.dto.PatchTeamRequest;
import pl.edu.pg.eti.kask.team.dto.PutTeamRequest;

import java.util.UUID;

@Path("")
public interface TeamController {
    @GET
    @Path("/teams")
    @Produces(MediaType.APPLICATION_JSON)
    GetTeamsResponse getTeams();

    @GET
    @Path("/teams/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetTeamResponse getTeam(@PathParam("id") UUID id);

    @PUT
    @Path("/teams/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putTeam(@PathParam("id") UUID id, PutTeamRequest request);

    @PATCH
    @Path("/teams/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchTeam(@PathParam("id") UUID id, PatchTeamRequest request);

    @DELETE
    @Path("/teams/{id}")
    void deleteTeam(@PathParam("id") UUID id);

}
