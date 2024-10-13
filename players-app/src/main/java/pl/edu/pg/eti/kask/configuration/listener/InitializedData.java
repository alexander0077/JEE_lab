package pl.edu.pg.eti.kask.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.service.AgentService;

import java.io.InputStream;
import java.util.UUID;

@WebListener
public class InitializedData implements ServletContextListener {
    
    private AgentService agentService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        agentService = (AgentService) event.getServletContext().getAttribute("agentService");
        init();
    }

    @SneakyThrows
    private void init() {
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
