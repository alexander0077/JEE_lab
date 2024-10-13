package pl.edu.pg.eti.kask.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.agent.service.AgentService;
import pl.edu.pg.eti.kask.agent.controller.AgentController;
import pl.edu.pg.eti.kask.component.DtoFunctionFactory;

@WebListener
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        AgentService agentService = (AgentService) event.getServletContext().getAttribute("agentService");

        event.getServletContext().setAttribute("agentController", new AgentController(
                agentService,
                new DtoFunctionFactory()
        ));

    }
}
