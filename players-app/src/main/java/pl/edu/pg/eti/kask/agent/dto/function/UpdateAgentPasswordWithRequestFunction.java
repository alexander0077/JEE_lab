package pl.edu.pg.eti.kask.agent.dto.function;

import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.dto.PutPasswordRequest;

import java.util.function.BiFunction;

public class UpdateAgentPasswordWithRequestFunction implements BiFunction<Agent, PutPasswordRequest, Agent> {

    @Override
    public Agent apply(Agent entity, PutPasswordRequest request) {
        return Agent.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .name(entity.getName())
                .surname(entity.getSurname())
                .age(entity.getAge())
                .active(entity.isActive())
                .email(entity.getEmail())
                .password(request.getPassword())
                .build();
    }

}
