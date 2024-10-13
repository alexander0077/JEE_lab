package pl.edu.pg.eti.kask.agent.dto.function;


import pl.edu.pg.eti.kask.agent.dto.PatchAgentRequest;
import pl.edu.pg.eti.kask.agent.entity.Agent;

import java.util.function.BiFunction;

public class UpdateAgentWithRequestFunction implements BiFunction<Agent, PatchAgentRequest, Agent> {

    @Override
    public Agent apply(Agent entity, PatchAgentRequest request) {
        return Agent.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .name(request.getName())
                .surname(entity.getSurname())
                .age(entity.getAge())
                .active(request.isActive())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .build();
    }

}
