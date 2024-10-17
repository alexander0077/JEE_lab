package pl.edu.pg.eti.kask.player.dto.function;

import pl.edu.pg.eti.kask.player.dto.GetPlayerResponse;
import pl.edu.pg.eti.kask.player.entity.Player;

import java.util.UUID;
import java.util.function.Function;

public class PlayerToResponseFunction implements Function<Player, GetPlayerResponse> {

    @Override
    public GetPlayerResponse apply(Player entity) {
        return GetPlayerResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .shirtNumber(entity.getShirtNumber())
                .position(entity.getPosition())
                .team(GetPlayerResponse.Team.builder()
                        .id(entity.getTeam().getId())
                        .name(entity.getTeam().getName())
                        .build())
                .build();
    }

}

