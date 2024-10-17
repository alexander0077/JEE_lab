package pl.edu.pg.eti.kask.player.repository.api;

import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.team.entity.Team;
import pl.edu.pg.eti.kask.repository.api.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends Repository<Player, UUID>{
    Optional<Player> findByIdAndAgent(UUID id, Agent user);
    List<Player> findAllByAgent(Agent user);
    List<Player> findAllByTeam(Team profession);
}
