package pl.edu.pg.eti.kask.agent.controller.api;

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
import pl.edu.pg.eti.kask.agent.dto.GetAgentResponse;
import pl.edu.pg.eti.kask.agent.dto.GetAgentsResponse;
import pl.edu.pg.eti.kask.agent.dto.PatchAgentRequest;
import pl.edu.pg.eti.kask.agent.dto.PutAgentRequest;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface AgentController {

    @GET
    @Path("/agents")
    @Produces(MediaType.APPLICATION_JSON)
    GetAgentsResponse getAgents();

    @GET
    @Path("/agents/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetAgentResponse getAgent(@PathParam("id") UUID id);

    @PUT
    @Path("/agents/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putAgent(@PathParam("id") UUID id, PutAgentRequest request);

    @PATCH
    @Path("/agents/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchAgent(@PathParam("id")UUID id, PatchAgentRequest request);

    @DELETE
    @Path("/agents/{id}")
    void deleteAgent(@PathParam("id") UUID id);

    @GET
    @Path("/agents/{id}/portrait")
    @Produces("image/png")
    public byte[] getAgentPortrait(@PathParam("id") UUID id);


    @PUT
    @Path("/agents/{id}/portrait")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void putAgentPortrait(
            @PathParam("id") UUID id,
            @SuppressWarnings("RestParamTypeInspection") @FormParam("portrait") InputStream portrait
    );


    @PATCH
    @Path("/agents/{id}/portrait")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void patchAgentPortrait(
            @PathParam("id") UUID id,
            @SuppressWarnings("RestParamTypeInspection") @FormParam("portrait") InputStream portrait
    );

    @DELETE
    @Path("/agents/{id}")
    void deleteAgentPortrait(@PathParam("id") UUID id);
}
