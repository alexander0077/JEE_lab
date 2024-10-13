package pl.edu.pg.eti.kask.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.agent.repository.api.AgentRepository;
import pl.edu.pg.eti.kask.agent.repository.memory.AgentInMemoryRepository;
import pl.edu.pg.eti.kask.agent.service.AgentService;
import pl.edu.pg.eti.kask.crypto.component.Pbkdf2PasswordHash;
import pl.edu.pg.eti.kask.datastore.component.DataStore;

import java.nio.file.Path;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        Path portraitStore = Path.of("players-app/src/main/java/pl/edu/pg/eti/kask/datastore/portraitStore");

        AgentRepository userRepository = new AgentInMemoryRepository(dataSource);

        event.getServletContext().setAttribute("agentService", new AgentService(userRepository, new Pbkdf2PasswordHash(), portraitStore));
    }

}
