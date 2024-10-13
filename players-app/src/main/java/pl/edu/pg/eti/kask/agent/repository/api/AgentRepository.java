package pl.edu.pg.eti.kask.agent.repository.api;

import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.repository.api.Repository;

import java.util.Optional;
import java.util.UUID;

public interface AgentRepository extends Repository<Agent, UUID> {

    Optional<Agent> findByLogin(String login);

}
