package pl.edu.pg.eti.kask.player.model.function;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.model.PlayerEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdatePlayerWithModelFunction implements BiFunction<Player, PlayerEditModel, Player>, Serializable {

    @Override
    @SneakyThrows
    public Player apply(Player entity, PlayerEditModel request) {
        return Player.builder()
                .id(entity.getId())
                .name(request.getName())
                .surname(entity.getSurname())
                .shirtNumber(request.getShirtNumber())
                .position(request.getPosition())
                .team(entity.getTeam())
                .agent(entity.getAgent())
                .build();
    }

}
