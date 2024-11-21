package pl.edu.pg.eti.kask.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.entity.AgentRoles;
import pl.edu.pg.eti.kask.agent.repository.api.AgentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {
    private final AgentRepository agentRepository;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InitializeAdminService(
            AgentRepository agentRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.agentRepository = agentRepository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (agentRepository.findByLogin("admin-service").isEmpty()) {

            Agent admin = Agent.builder()
                    .id(UUID.fromString("14d59f3a-057c-44d5-825a-19295a6600a8"))
                    .login("admin-service")
                    .name("Admin")
                    .surname("Service")
                    .email("admin-service@simplerpg.example.com")
                    .password(passwordHash.generate("adminadmin".toCharArray()))
                    .age(30)
                    .active(true)
                    .roles(List.of(AgentRoles.ADMIN, AgentRoles.USER))
                    .build();

            agentRepository.create(admin);
        }
    }

}
