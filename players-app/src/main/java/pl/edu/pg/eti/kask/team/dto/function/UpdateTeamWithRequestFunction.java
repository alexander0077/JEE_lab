package pl.edu.pg.eti.kask.team.dto.function;

import pl.edu.pg.eti.kask.team.dto.PatchTeamRequest;
import pl.edu.pg.eti.kask.team.entity.Team;

import java.util.function.BiFunction;

public class UpdateTeamWithRequestFunction implements BiFunction<Team, PatchTeamRequest, Team> {

    @Override
    public Team apply(Team entity, PatchTeamRequest request) {
        return Team.builder()
                .id(entity.getId())
                .name(request.getName())
                .budget(request.getBudget())
                .isProfessional(entity.isProfessional())
                .build();
    }

}
