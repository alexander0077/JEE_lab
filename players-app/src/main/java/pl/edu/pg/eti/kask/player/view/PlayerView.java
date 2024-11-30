package pl.edu.pg.eti.kask.player.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.model.PlayerModel;
import pl.edu.pg.eti.kask.player.service.PlayerService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class PlayerView implements Serializable {

    private PlayerService service;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private PlayerModel player;


    @Inject
    public PlayerView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(PlayerService service) {
        this.service = service;
    }


    public void init() throws IOException {
        Optional<Player> player = service.findForCallerPrincipal(id);
        if (player.isPresent()) {
            this.player = factory.playerToModel().apply(player.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Player not found");
        }
    }

}
