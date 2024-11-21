package pl.edu.pg.eti.kask.player.model.function;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.entity.AgentRoles;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.model.PlayerEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdatePlayerWithModelFunction implements BiFunction<Player, PlayerEditModel, Player>, Serializable {

    @Override
    @SneakyThrows
    public Player apply(Player entity, PlayerEditModel model) {
        return Player.builder()
                .id(entity.getId())
                .name(model.getName())
                .surname(entity.getSurname())
                .shirtNumber(model.getShirtNumber())
                .position(model.getPosition())
                .team(entity.getTeam())
                .agent(Agent.builder()
                        .id(model.getAgent().getId())
                        .build())
                .build();
    }

}
