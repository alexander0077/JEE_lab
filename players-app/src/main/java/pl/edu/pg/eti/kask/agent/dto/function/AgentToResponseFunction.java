package pl.edu.pg.eti.kask.agent.dto.function;


import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.dto.GetAgentResponse;

import java.util.function.Function;

public class AgentToResponseFunction implements Function<Agent, GetAgentResponse> {

    @Override
    public GetAgentResponse apply(Agent agent) {
        return GetAgentResponse.builder()
                .id(agent.getId())
                .login(agent.getLogin())
                .email(agent.getEmail())
                .name(agent.getName())
                .surname(agent.getSurname())
                .age(agent.getAge())
                .active(agent.isActive())
                .build();
    }

}
