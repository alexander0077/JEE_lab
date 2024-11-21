package pl.edu.pg.eti.kask.agent.model.function;

import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.model.AgentsModel;

import java.util.List;
import java.util.function.Function;

public class AgentsToModelFunction implements Function<List<Agent>, AgentsModel> {
    @Override
    public AgentsModel apply(List<Agent> entity) {
        return AgentsModel.builder()
                .agents(entity.stream()
                        .map(player -> AgentsModel.Agent.builder()
                                .id(player.getId())
                                .login(player.getLogin())
                                .build())
                        .toList())
                .build();
    }

}

