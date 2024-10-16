package pl.edu.pg.eti.kask.agent.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.agent.controller.api.AgentController;
import pl.edu.pg.eti.kask.agent.dto.GetAgentResponse;
import pl.edu.pg.eti.kask.agent.dto.GetAgentsResponse;
import pl.edu.pg.eti.kask.agent.dto.PatchAgentRequest;
import pl.edu.pg.eti.kask.agent.dto.PutAgentRequest;
import pl.edu.pg.eti.kask.agent.service.AgentService;
import pl.edu.pg.eti.kask.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.controller.servlet.exception.BadRequestException;


import java.io.InputStream;
import java.util.UUID;

@RequestScoped
public class AgentSimpleController implements AgentController {
    private final AgentService service;
    private final DtoFunctionFactory factory;

    @Inject
    public AgentSimpleController(AgentService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public GetAgentsResponse getAgents() {
        return factory.agentsToResponse().apply(service.findAll());
    }

    public GetAgentResponse getAgent(UUID uuid) {
        return service.find(uuid)
                .map(factory.agentToResponse())
                .orElseThrow(NotFoundException::new);
    }

    public void putAgent(UUID id, PutAgentRequest request) {
        try {
            service.create(factory.requestToAgent().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    public void patchAgent(UUID id, PatchAgentRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateAgent().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    public void deleteAgent(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    public byte[] getAgentPortrait(UUID id) {
        return service.find(id)
                .map(agent -> {
                    if (agent.getPortrait() == null) throw new NotFoundException("No portrait for this agent.");
                    return service.getAgentPortrait(id);
                })
                .orElseThrow(() -> new NotFoundException("No agent found"));
    }

    public void putAgentPortrait(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                entity -> {
                    if (entity.getPortrait() != null) throw new BadRequestException("This agent already has portrait.");
                    service.updatePortrait(id, portrait);
                },
                () -> {
                    throw new NotFoundException("The agent with id \"%s\" does not exist".formatted(id));
                }
        );
    }

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
