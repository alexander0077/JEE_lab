package pl.edu.pg.eti.kask.player.dto.function;

import pl.edu.pg.eti.kask.player.dto.GetPlayersResponse;
import pl.edu.pg.eti.kask.player.entity.Player;

import java.util.List;
import java.util.function.Function;

public class PlayersToResponseFunction implements Function<List<Player>, GetPlayersResponse> {
    @Override
    public GetPlayersResponse apply(List<Player> entities) {
        return GetPlayersResponse.builder()
                .players(entities.stream()
                        .map(character -> GetPlayersResponse.Player.builder()
                                .id(character.getId())
                                .name(character.getName())
                                .build())
                        .toList())
                .build();
    }

}
