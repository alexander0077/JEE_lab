package pl.edu.pg.eti.kask.player.model.function;

import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.model.PlayerEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class PlayerToEditModelFunction implements Function<Player, PlayerEditModel>, Serializable {
    @Override
    public PlayerEditModel apply(Player entity) {
        return PlayerEditModel.builder()
                .name(entity.getName())
                .shirtNumber(entity.getShirtNumber())
                .position(entity.getPosition())
                .build();
    }

}
