package pl.edu.pg.eti.kask.component;

import jakarta.enterprise.context.ApplicationScoped;
import pl.edu.pg.eti.kask.agent.model.function.AgentToModelFunction;
import pl.edu.pg.eti.kask.agent.model.function.AgentsToModelFunction;
import pl.edu.pg.eti.kask.player.model.function.*;
import pl.edu.pg.eti.kask.player.model.function.TeamToModelFunction;
import pl.edu.pg.eti.kask.team.model.function.TeamToViewModelFunction;
import pl.edu.pg.eti.kask.team.model.function.TeamsToModelFunction;

@ApplicationScoped
public class ModelFunctionFactory {

    public PlayerToModelFunction playerToModel() {
        return new PlayerToModelFunction();
    }

    public PlayersToModelFunction playersToModel() {
        return new PlayersToModelFunction();
    }

    public PlayerToEditModelFunction playerToEditModel() {
        return new PlayerToEditModelFunction(agentToModel());
    }

    public ModelToPlayerFunction modelToPlayer() {
        return new ModelToPlayerFunction();
    }

    public TeamToModelFunction teamToModel() {
        return new TeamToModelFunction();
    }

    public TeamToViewModelFunction teamToViewModel() {
        return new TeamToViewModelFunction();
    }

    public TeamsToModelFunction teamsToModel() {
        return new TeamsToModelFunction();
    }

    public UpdatePlayerWithModelFunction updatePlayer() {
        return new UpdatePlayerWithModelFunction();
    }

    public AgentToModelFunction agentToModel() {
        return new AgentToModelFunction();
    }

    public AgentsToModelFunction agentsToModel() {
        return new AgentsToModelFunction();
    }
}

