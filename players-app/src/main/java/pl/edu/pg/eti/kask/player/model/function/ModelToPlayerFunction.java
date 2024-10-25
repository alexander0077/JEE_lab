package pl.edu.pg.eti.kask.player.model.function;


import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.model.PlayerCreateModel;
import pl.edu.pg.eti.kask.team.entity.Team;

import java.io.Serializable;
import java.util.function.Function;


public class ModelToPlayerFunction implements Function<PlayerCreateModel, Player>, Serializable {

    @Override
    @SneakyThrows
    public Player apply(PlayerCreateModel model) {
        return Player.builder()
                .id(model.getId())
                .name(model.getName())
                .surname(model.getSurname())
                .shirtNumber(model.getShirtNumber())
                .position(model.getPosition())
                .team(Team.builder()
                        .id(model.getTeam().getId())
                        .build())
                .build();
    }

}
