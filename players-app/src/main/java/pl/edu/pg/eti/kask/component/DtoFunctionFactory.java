package pl.edu.pg.eti.kask.component;


import jakarta.enterprise.context.ApplicationScoped;
import pl.edu.pg.eti.kask.agent.dto.function.*;

@ApplicationScoped
public class DtoFunctionFactory {

    public RequestToAgentFunction requestToAgent() {
        return new RequestToAgentFunction();
    }

    public UpdateAgentWithRequestFunction updateAgent() {
        return new UpdateAgentWithRequestFunction();
    }

    public UpdateAgentPasswordWithRequestFunction updateAgentPassword() {
        return new UpdateAgentPasswordWithRequestFunction();
    }

    public AgentsToResponseFunction agentsToResponse() {
        return new AgentsToResponseFunction();
    }

    public AgentToResponseFunction agentToResponse() {
        return new AgentToResponseFunction();
    }

}
