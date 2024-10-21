package pl.edu.pg.eti.kask.player.dto.function;

import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.player.dto.PutPlayerRequest;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.team.entity.Team;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToPlayerFunction implements BiFunction<UUID, PutPlayerRequest, Player> {
    @Override
    public Player apply(UUID id, PutPlayerRequest request) {
        return Player.builder()
                .id(id)
                .name(request.getName())
                .surname(request.getSurname())
                .shirtNumber(request.getShirtNumber())
                .position(request.getPosition())
                .team(Team.builder()
                        .id(request.getTeam())
                        .build())
                .agent(Agent.builder()
                        .id(request.getAgent())
                        .build())
                .build();
    }
}
