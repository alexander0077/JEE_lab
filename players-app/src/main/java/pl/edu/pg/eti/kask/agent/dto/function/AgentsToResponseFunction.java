package pl.edu.pg.eti.kask.agent.dto.function;

import pl.edu.pg.eti.kask.agent.dto.GetAgentsResponse;
import pl.edu.pg.eti.kask.agent.entity.Agent;

import java.util.List;
import java.util.function.Function;

public class AgentsToResponseFunction implements Function<List<Agent>, GetAgentsResponse> {

    @Override
    public GetAgentsResponse apply(List<Agent> agents) {
        return GetAgentsResponse.builder()
                .agents(agents.stream()
                        .map(agent -> GetAgentsResponse.Agent.builder()
                                .id(agent.getId())
                                .name(agent.getName())
                                .surname(agent.getSurname())
                                .build())
                        .toList())
                .build();
    }

}
