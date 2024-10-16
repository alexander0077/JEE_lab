package pl.edu.pg.eti.kask.agent.controller.api;

import pl.edu.pg.eti.kask.agent.dto.GetAgentResponse;
import pl.edu.pg.eti.kask.agent.dto.GetAgentsResponse;
import pl.edu.pg.eti.kask.agent.dto.PatchAgentRequest;
import pl.edu.pg.eti.kask.agent.dto.PutAgentRequest;

import java.io.InputStream;
import java.util.UUID;

public interface AgentController {

    GetAgentsResponse getAgents();

    GetAgentResponse getAgent(UUID uuid);
    void putAgent(UUID id, PutAgentRequest request);

    void patchAgent(UUID id, PatchAgentRequest request);

    void deleteAgent(UUID id);

    byte[] getAgentPortrait(UUID id);

    void putAgentPortrait(UUID id, InputStream portrait);

    void patchAgentPortrait(UUID id, InputStream portrait);

    void deleteAgentPortrait(UUID id);
}
