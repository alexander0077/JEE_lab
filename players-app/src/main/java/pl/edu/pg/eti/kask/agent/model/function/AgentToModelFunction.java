package pl.edu.pg.eti.kask.agent.model.function;

import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.model.AgentModel;

import java.io.Serializable;
import java.util.function.Function;

public class AgentToModelFunction implements Function<Agent, AgentModel>, Serializable {

    @Override
    public AgentModel apply(Agent entity) {
        return AgentModel.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .build();
    }
}

