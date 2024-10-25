package pl.edu.pg.eti.kask.player.view;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.player.entity.PositionTypes;
import pl.edu.pg.eti.kask.player.model.PlayerCreateModel;
import pl.edu.pg.eti.kask.player.service.PlayerService;
import pl.edu.pg.eti.kask.player.model.TeamModel;
import pl.edu.pg.eti.kask.team.service.TeamService;

import javax.swing.text.Position;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class PlayerCreate implements Serializable {
    
    private final PlayerService playerService;
    private final TeamService teamService;
    private final ModelFunctionFactory factory;

    @Getter
    private PlayerCreateModel player;
    
    @Getter
    private List<TeamModel> teams;

    private final Conversation conversation;
    
    @Inject
    public PlayerCreate(
            PlayerService playerService,
            TeamService teamService,
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.playerService = playerService;
        this.factory = factory;
        this.teamService = teamService;
        this.conversation = conversation;
    }

    public void init() {
        if (conversation.isTransient()) {
            teams = teamService.findAll().stream()
                    .map(factory.teamToModel())
                    .collect(Collectors.toList());
            player = PlayerCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public String goToTeamAction() {
        return "/player/player_create__team.xhtml?faces-redirect=true";
    }

    public Object goToBasicAction() {
        return "/player/player_create__basic.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/team/team_list.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/player/player_create__confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        playerService.create(factory.modelToPlayer().apply(player));
        conversation.end();
        return "/team/team_view.xhtml?faces-redirect=true&id=" + player.getTeam().getId();
    }

    public String getConversationId() {
        return conversation.getId();
    }
}

