package pl.edu.pg.eti.kask.team.controller.api;

import pl.edu.pg.eti.kask.team.dto.GetTeamResponse;
import pl.edu.pg.eti.kask.team.dto.GetTeamsResponse;

import java.util.UUID;

public interface TeamController {
    GetTeamsResponse getTeams();

    GetTeamResponse getTeam(UUID id);
}
