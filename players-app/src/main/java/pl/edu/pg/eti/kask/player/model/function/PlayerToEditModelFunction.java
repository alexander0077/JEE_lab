package pl.edu.pg.eti.kask.player.model.function;

import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.model.PlayerEditModel;
import pl.edu.pg.eti.kask.agent.model.function.AgentToModelFunction;

import java.io.Serializable;
import java.util.function.Function;

public class PlayerToEditModelFunction implements Function<Player, PlayerEditModel>, Serializable {
    private final AgentToModelFunction agentToModelFunction;

    public PlayerToEditModelFunction(AgentToModelFunction agentToModelFunction) {
        this.agentToModelFunction = agentToModelFunction;
    }

    @Override
    public PlayerEditModel apply(Player entity) {
        return PlayerEditModel.builder()
                .name(entity.getName())
                .shirtNumber(entity.getShirtNumber())
                .position(entity.getPosition())
                .agent(agentToModelFunction.apply(entity.getAgent()))
                .version(entity.getVersion())
                .build();
    }

}
