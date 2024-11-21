package pl.edu.pg.eti.kask.agent.dto.function;

import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.dto.PutAgentRequest;
import pl.edu.pg.eti.kask.agent.entity.AgentRoles;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToAgentFunction implements BiFunction<UUID, PutAgentRequest, Agent> {

    @Override
    public Agent apply(UUID id, PutAgentRequest request) {
        return Agent.builder()
                .id(id)
                .login(request.getLogin())
                .name(request.getName())
                .surname(request.getSurname())
                .age(request.getAge())
                .active(request.isActive())
                .password(request.getPassword())
                .roles(List.of(AgentRoles.USER))
                .email(request.getEmail())
                .build();
    }

}
