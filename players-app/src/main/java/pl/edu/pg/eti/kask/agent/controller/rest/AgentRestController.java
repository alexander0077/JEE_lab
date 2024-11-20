package pl.edu.pg.eti.kask.agent.controller.rest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.agent.controller.api.AgentController;
import pl.edu.pg.eti.kask.agent.dto.GetAgentResponse;
import pl.edu.pg.eti.kask.agent.dto.GetAgentsResponse;
import pl.edu.pg.eti.kask.agent.dto.PatchAgentRequest;
import pl.edu.pg.eti.kask.agent.dto.PutAgentRequest;
import pl.edu.pg.eti.kask.agent.service.AgentService;
import pl.edu.pg.eti.kask.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.player.view.PlayerList;

import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class AgentRestController implements AgentController {
    private final AgentService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }


    @Inject
    public AgentRestController(AgentService service, DtoFunctionFactory factory,
                               @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetAgentsResponse getAgents() {
        return factory.agentsToResponse().apply(service.findAll());
    }

    @Override
    public GetAgentResponse getAgent(UUID id) {
        return service.find(id)
                .map(factory.agentToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @SneakyThrows
    @Override
    public void putAgent(UUID id, PutAgentRequest request) {
        try {
            service.create(factory.requestToAgent().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(AgentController.class, "getAgent")
                    .build(id)
                    .toString());

            throw new WebApplicationException(Response.Status.CREATED);
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;

        }
    }

    @Override
    public void patchAgent(UUID id, PatchAgentRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateAgent().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteAgent(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getAgentPortrait(UUID id) {
        return service.find(id)
                .map(agent -> {
                    if (agent.getPortrait() == null) throw new NotFoundException("No portrait for this agent.");
                    return service.getAgentPortrait(id);
                })
                .orElseThrow(() -> new NotFoundException("No agent found"));
    }

    @Override
    public void putAgentPortrait(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                entity -> {
                    if (entity.getPortrait() != null) throw new BadRequestException("This agent already has portrait.");
                    service.updatePortrait(id, portrait);
                    response.setHeader("Location", uriInfo.getBaseUriBuilder()
                            .path(AgentController.class, "getAgentPortrait")
                            .build(id)
                            .toString());

                    throw new WebApplicationException(Response.Status.CREATED);
                },
                () -> {
                    throw new NotFoundException("The agent with id \"%s\" does not exist".formatted(id));
                }
        );
    }

    @Override
    public void patchAgentPortrait(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                entity -> {
                    if (entity.getPortrait() == null) throw new BadRequestException("This agent doesn't have any portrait.");
                    service.updatePortrait(id, portrait);
                },
                () -> {
                    throw new NotFoundException("The agent with id \"%s\" does not exist".formatted(id));
                }
        );
    }

    @Override
    public void deleteAgentPortrait(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> {
                    if (entity.getPortrait() == null) throw new BadRequestException("This agent doesn't have any portrait.");
                    service.deletePortrait(id);
                },
                () -> {
                    throw new NotFoundException("The agent with id \"%s\" does not exist".formatted(id));
                }
        );
    }
}
