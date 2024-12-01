package pl.edu.pg.eti.kask.team.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.player.service.PlayerService;
import pl.edu.pg.eti.kask.team.entity.Team;
import pl.edu.pg.eti.kask.team.model.TeamViewModel;
import pl.edu.pg.eti.kask.team.service.TeamService;
import pl.edu.pg.eti.kask.player.entity.Player;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class TeamView implements Serializable {

    private final TeamService service;
    private final PlayerService playerService;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private TeamViewModel team;


    @Inject
    public TeamView(TeamService service, ModelFunctionFactory factory, PlayerService playerService) {
        this.service = service;
        this.playerService = playerService;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Team> team = service.find(id);
        List<Player> players = new ArrayList<>();
        if (!playerService.findAllByTeam(id).isEmpty()) {
            players = playerService.findAllByTeam(id).get();
        }

        if (team.isPresent()) {
            this.team = factory.teamToViewModel().apply(team.get(), players);
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Team not found");
        }
    }

    public void deleteAction(UUID playerId) {
        playerService.delete(playerId);
//        return "team_view?faces-redirect=true&id="+id;
        team = null;
        try {
            init();
        } catch (Exception e) {
        }
    }

}