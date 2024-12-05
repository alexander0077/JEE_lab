package pl.edu.pg.eti.kask.team.model.function;

import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.team.entity.Team;
import pl.edu.pg.eti.kask.team.model.TeamViewModel;

import java.util.List;
import java.util.function.BiFunction;


public class TeamToViewModelFunction implements BiFunction<Team, List<Player>, TeamViewModel> {

    @Override
    public TeamViewModel apply(Team entity, List<Player> players) {
        return TeamViewModel.builder()
                .players(players.stream()
                        .map(player -> TeamViewModel.Player.builder()
                                .id(player.getId())
                                .name(player.getName())
                                .version(player.getVersion())
                                .creationDateTime(player.getCreationDateTime())
                                .build())
                        .toList())
                .name(entity.getName())
                .budget(entity.getBudget())
                .isProfessional(entity.isProfessional())
                .build();
    }
}
