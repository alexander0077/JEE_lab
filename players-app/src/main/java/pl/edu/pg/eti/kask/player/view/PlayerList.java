package pl.edu.pg.eti.kask.player.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.player.model.PlayersModel;
import pl.edu.pg.eti.kask.player.service.PlayerService;
import pl.edu.pg.eti.kask.component.ModelFunctionFactory;

@RequestScoped
@Named
public class PlayerList {

    private final PlayerService service;
    private PlayersModel players;

    private final ModelFunctionFactory factory;

    @Inject
    public PlayerList(PlayerService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public PlayersModel getPlayers() {
        if (players == null) {
            players = factory.playersToModel().apply(service.findAll());
        }
        return players;
    }

    public String deleteAction(PlayersModel.Player player) {
        service.delete(player.getId());
        return "player_list?faces-redirect=true";
    }

}
