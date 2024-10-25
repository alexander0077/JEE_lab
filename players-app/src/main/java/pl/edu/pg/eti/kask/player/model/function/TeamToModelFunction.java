package pl.edu.pg.eti.kask.player.model.function;

import pl.edu.pg.eti.kask.team.entity.Team;
import pl.edu.pg.eti.kask.player.model.TeamModel;

import java.io.Serializable;
import java.util.function.Function;

public class TeamToModelFunction implements Function<Team, TeamModel>, Serializable {

    @Override
    public TeamModel apply(Team entity) {
        return TeamModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .budget(entity.getBudget())
                .isProfessional(entity.isProfessional())
                .build();
    }

}
