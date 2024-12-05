package pl.edu.pg.eti.kask.player.view;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
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

    private PlayerService service;
    private final ModelFunctionFactory factory;

    private final FacesContext facesContext;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private PlayerEditModel player;

    @Inject
    public PlayerEdit(ModelFunctionFactory factory, FacesContext facesContext) {
        this.factory = factory;
        this.facesContext = facesContext;
    }

    @EJB
    public void setService(PlayerService service) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<Player> player = service.findForCallerPrincipal(id);
        if (player.isPresent()) {
            this.player = factory.playerToEditModel().apply(player.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Player not found");
        }
    }

    public String saveAction() throws IOException {
        PlayerEditModel playerState = player;
        try {
            service.update(factory.updatePlayer().apply(service.find(id).orElseThrow(), player));
            return "/player/player_view?id=" + id + "&faces-redirect=true";
        } catch (Exception ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                init();
                String message = "UWAGA: Obiekt jest nieaktualny i nie można go zaktualizować.";
                message += "Stan obiektu aktualnie w bazie:";
                message += service.find(id).toString();
                message += "\nJeżeli jesteś pewny ze chcesz edytować ten element, wybierz \'Zapisz\' ponownie";
                facesContext.addMessage(null, new FacesMessage(message));
                player = playerState;
            }
            return null ;
        }

    }

    public String cancelAction() {
        return "/player/player_view?id=" + id + "&faces-redirect=true";
    }
}

