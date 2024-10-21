package pl.edu.pg.eti.kask.configuration.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.context.Initialized;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.service.AgentService;
import pl.edu.pg.eti.kask.player.entity.Player;
import pl.edu.pg.eti.kask.player.entity.PositionTypes;
import pl.edu.pg.eti.kask.player.service.PlayerService;
import pl.edu.pg.eti.kask.team.entity.Team;
import pl.edu.pg.eti.kask.team.service.TeamService;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {
    
    private final AgentService agentService;
    private final PlayerService playerService;
    private final TeamService teamService;
    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(
            AgentService agentService,
            PlayerService playerService,
            TeamService teamService,
            RequestContextController requestContextController
    ) {
        this.agentService = agentService;
        this.playerService = playerService;
        this.teamService = teamService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }


    @SneakyThrows
    private void init() {
        requestContextController.activate();

        Agent raiola = Agent.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .login("mino123")
                .name("Mino")
                .surname("Raiola")
                .age(57)
                .active(false)
                .email("minoraiola@example.com")
                .password("minopass")
                .build();

        Agent zahavi = Agent.builder()
                .id(UUID.fromString("b1c02c36-8af1-439d-8864-20ef79849482"))
                .login("pini123")
                .name("Pini")
                .surname("Zahavi")
                .age(82)
                .active(true)
                .email("pinizahavi@example.com")
                .password("pinipass")
                .build();

        Agent romano = Agent.builder()
                .id(UUID.fromString("6aee5e1d-bf0f-4936-85fd-9afcb785cea4"))
                .login("fabrizio123")
                .name("Fabrizio")
                .surname("Romano")
                .age(31)
                .active(true)
                .email("fabrizio.romano@example.com")
                .password("herewego")
                .build();

        Agent struth = Agent.builder()
                .id(UUID.fromString("cbddcea6-4f49-4806-ac45-9f94ff976ef1"))
                .login("best_football_agent")
                .name("Volker")
                .surname("Struth")
                .age(58)
                .active(true)
                .email("struth123@example.com")
                .password("password321")
                .build();

        agentService.create(raiola);
        agentService.create(zahavi);
        agentService.create(romano);
        agentService.create(struth);

        Team barcelona = Team.builder()
                .id(UUID.fromString("a8217a53-9dec-4f57-ab31-5c47e20f0262"))
                .name("FC Barcelona")
                .isProfessional(true)
                .budget(200000000)
                .build();

        Team mancity = Team.builder()
                .id(UUID.fromString("3b999cd8-0f1e-4e20-935f-2a7a0a09475b"))
                .name("Manchester City")
                .isProfessional(true)
                .budget(1000000000)
                .build();

        Player lewandowski = Player.builder()
                .id(UUID.fromString("def6a96f-9a8d-4a06-b0b5-5f2d4f01e9ac"))
                .name("Robert")
                .surname("Lewandowski")
                .shirtNumber(9)
                .position(PositionTypes.STRIKER)
                .team(barcelona)
                .agent(zahavi)
                .build();

        Player yamal = Player.builder()
                .id(UUID.fromString("f1398347-f46b-4ef8-ae75-aa1538e5c21f"))
                .name("Lamine")
                .surname("Yamal")
                .shirtNumber(19)
                .position(PositionTypes.STRIKER)
                .team(barcelona)
                .agent(raiola)
                .build();

        Player grealish = Player.builder()
                .id(UUID.fromString("4c603256-e157-41f2-87a6-f72e3fdc32b0"))
                .name("Jack")
                .surname("Grealish")
                .shirtNumber(10)
                .position(PositionTypes.MIDFIELDER)
                .team(mancity)
                .agent(raiola)
                .build();

        Player walker = Player.builder()
                .id(UUID.fromString("911b42cc-dc42-4100-8f96-f87aa456dde3"))
                .name("Kyle")
                .surname("Walker")
                .shirtNumber(2)
                .position(PositionTypes.DEFENDER)
                .team(mancity)
                .agent(romano)
                .build();

        teamService.create(barcelona);
        teamService.create(mancity);

        playerService.create(lewandowski);
        playerService.create(yamal);
        playerService.create(grealish);
        playerService.create(walker);

        requestContextController.deactivate();
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }

}
