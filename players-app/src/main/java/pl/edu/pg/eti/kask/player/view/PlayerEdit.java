package pl.edu.pg.eti.kask.player.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.model.PlayerEditModel;
import pl.edu.pg.eti.kask.player.service.PlayerService;
import pl.edu.pg.eti.kask.component.ModelFunctionFactory;

import java.io.IOException;
import java.io.Serializable;

import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class PlayerEdit implements Serializable {

    private final PlayerService service;
    private final ModelFunctionFactory factory;


    @Setter
    @Getter
    private UUID id;

    @Getter
    private PlayerEditModel player;

    @Inject
    public PlayerEdit(PlayerService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Player> player = service.find(id);
        if (player.isPresent()) {
            this.player = factory.playerToEditModel().apply(player.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Player not found");
        }
    }

    public String saveAction() {
        service.update(factory.updatePlayer().apply(service.find(id).orElseThrow(), player));
//        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
//        return viewId + "?faces-redirect=true&includeViewParams=true";
        return "/player/player_view?id=" + id + "&faces-redirect=true";
    }

    public String cancelAction() {
        return "/player/player_view?id=" + id + "&faces-redirect=true";
    }
}

