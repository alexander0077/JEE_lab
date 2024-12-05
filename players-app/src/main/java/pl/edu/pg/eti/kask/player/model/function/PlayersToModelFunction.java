package pl.edu.pg.eti.kask.player.model.function;

import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.model.PlayersModel;

import java.util.List;
import java.util.function.Function;

public class PlayersToModelFunction implements Function<List<Player>, PlayersModel> {

    @Override
    public PlayersModel apply(List<Player> entity) {
        return PlayersModel.builder()
                .players(entity.stream()
                        .map(player -> PlayersModel.Player.builder()
                                .id(player.getId())
                                .name(player.getName())
                                .version(player.getVersion())
                                .creationDateTime(player.getCreationDateTime())
                                .editionDateTime(player.getEditionDateTime())
                                .build())
                        .toList())
                .build();
    }
}