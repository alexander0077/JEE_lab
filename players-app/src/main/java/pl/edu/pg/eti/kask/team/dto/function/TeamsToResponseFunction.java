package pl.edu.pg.eti.kask.team.dto.function;

import pl.edu.pg.eti.kask.team.dto.GetTeamsResponse;
import pl.edu.pg.eti.kask.team.entity.Team;

import java.util.List;
import java.util.function.Function;

public class TeamsToResponseFunction implements Function<List<Team>, GetTeamsResponse> {
    @Override
    public GetTeamsResponse apply(List<Team> entities) {
        return GetTeamsResponse.builder()
                .teams(entities.stream()
                        .map(team -> GetTeamsResponse.Team.builder()
                                .id(team.getId())
                                .name(team.getName())
                                .build())
                        .toList())
                .build();
    }

}
