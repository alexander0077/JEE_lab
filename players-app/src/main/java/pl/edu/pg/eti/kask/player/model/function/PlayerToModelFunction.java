package pl.edu.pg.eti.kask.player.model.function;

import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.model.PlayerModel;

import java.io.Serializable;
import java.util.function.Function;

public class PlayerToModelFunction implements Function<Player, PlayerModel>, Serializable {

    @Override
    public PlayerModel apply(Player entity) {
        return PlayerModel.builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .shirtNumber(entity.getShirtNumber())
                .position(entity.getPosition())
                .team(entity.getTeam().getName())
                .agent(entity.getAgent().getName())
                .build();
    }

}
