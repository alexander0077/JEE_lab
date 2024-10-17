package pl.edu.pg.eti.kask.player.dto.function;

import pl.edu.pg.eti.kask.player.dto.PatchPlayerRequest;
import pl.edu.pg.eti.kask.player.entity.Player;

import java.util.function.BiFunction;

public class UpdatePlayerWithRequestFunction implements BiFunction<Player, PatchPlayerRequest, Player> {

    @Override
    public Player apply(Player entity, PatchPlayerRequest request) {
        return Player.builder()
                .id(entity.getId())
                .name(request.getName())
                .surname(entity.getSurname())
                .shirtNumber(request.getShirtNumber())
                .position(request.getPosition())
                .team(entity.getTeam())
                .build();
    }

}