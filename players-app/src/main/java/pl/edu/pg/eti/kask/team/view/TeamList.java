package pl.edu.pg.eti.kask.team.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.team.model.TeamsModel;
import pl.edu.pg.eti.kask.team.service.TeamService;

@RequestScoped
@Named
public class TeamList {

    private final TeamService service;
    private TeamsModel teams;

    private final ModelFunctionFactory factory;

    @Inject
    public TeamList(TeamService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public TeamsModel getTeams() {
        if (teams == null) {
            teams = factory.teamsToModel().apply(service.findAll());
        }
        return teams;
    }

    public String deleteAction(TeamsModel.Team team) {
        service.delete(service.find(team.getId()).get());
        return "team_list?faces-redirect=true";
    }

}
