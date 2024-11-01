package pl.edu.pg.eti.kask.team.dto.function;

import pl.edu.pg.eti.kask.team.dto.PutTeamRequest;
import pl.edu.pg.eti.kask.team.entity.Team;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToTeamFunction implements BiFunction<UUID, PutTeamRequest, Team> {
    @Override
    public Team apply(UUID id, PutTeamRequest request) {
        return Team.builder()
                .id(id)
                .name(request.getName())
                .budget(request.getBudget())
                .isProfessional(request.isProfessional())
                .build();
    }
}