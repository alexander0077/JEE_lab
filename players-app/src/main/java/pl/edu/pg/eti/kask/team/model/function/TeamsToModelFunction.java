package pl.edu.pg.eti.kask.team.model.function;

import pl.edu.pg.eti.kask.team.entity.Team;
import pl.edu.pg.eti.kask.team.model.TeamsModel;

import java.util.List;
import java.util.function.Function;

public class TeamsToModelFunction implements Function<List<Team>, TeamsModel> {

    @Override
    public TeamsModel apply(List<Team> entity) {
        return TeamsModel.builder()
                .teams(entity.stream()
                        .map(team -> TeamsModel.Team.builder()
                                .id(team.getId())
                                .name(team.getName())
                                .build())
                        .toList())
                .build();
    }
}
