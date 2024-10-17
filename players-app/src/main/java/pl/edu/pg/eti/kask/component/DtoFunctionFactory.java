package pl.edu.pg.eti.kask.component;


import jakarta.enterprise.context.ApplicationScoped;
import pl.edu.pg.eti.kask.agent.dto.function.*;
import pl.edu.pg.eti.kask.player.dto.function.PlayerToResponseFunction;
import pl.edu.pg.eti.kask.player.dto.function.PlayersToResponseFunction;
import pl.edu.pg.eti.kask.player.dto.function.RequestToPlayerFunction;
import pl.edu.pg.eti.kask.player.dto.function.UpdatePlayerWithRequestFunction;
import pl.edu.pg.eti.kask.team.dto.function.TeamToResponseFunction;
import pl.edu.pg.eti.kask.team.dto.function.TeamsToResponseFunction;

@ApplicationScoped
public class DtoFunctionFactory {

    public RequestToAgentFunction requestToAgent() {
        return new RequestToAgentFunction();
    }

    public UpdateAgentWithRequestFunction updateAgent() {
        return new UpdateAgentWithRequestFunction();
    }

    public UpdateAgentPasswordWithRequestFunction updateAgentPassword() {
        return new UpdateAgentPasswordWithRequestFunction();
    }

    public AgentsToResponseFunction agentsToResponse() {
        return new AgentsToResponseFunction();
    }

    public AgentToResponseFunction agentToResponse() {
        return new AgentToResponseFunction();
    }

    public TeamToResponseFunction teamToResponse() {
        return new TeamToResponseFunction();
    }
    public TeamsToResponseFunction teamsToResponse() {
        return new TeamsToResponseFunction();
    }

    public PlayerToResponseFunction playerToResponse() {
        return new PlayerToResponseFunction();
    }
    
    public PlayersToResponseFunction playersToResponse() {
        return new PlayersToResponseFunction();
    }

    public RequestToPlayerFunction requestToPlayer() {
        return new RequestToPlayerFunction();
    }
    
    public UpdatePlayerWithRequestFunction updatePlayer() {
        return new UpdatePlayerWithRequestFunction();
    }
}
