package pl.edu.pg.eti.kask.team.dto.function;

import pl.edu.pg.eti.kask.team.dto.GetTeamResponse;
import pl.edu.pg.eti.kask.team.entity.Team;

import java.util.function.Function;

public class TeamToResponseFunction implements Function<Team, GetTeamResponse> {
    @Override
    public GetTeamResponse apply(Team entity) {
        return GetTeamResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .budget(entity.getBudget())
                .isProfessional(entity.isProfessional())
                .build();
    }

}
