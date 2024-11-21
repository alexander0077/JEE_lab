package pl.edu.pg.eti.kask.agent.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.agent.model.AgentsModel;
import pl.edu.pg.eti.kask.agent.service.AgentService;
import pl.edu.pg.eti.kask.component.ModelFunctionFactory;


@RequestScoped
@Named
public class AgentList {
    
    private final AgentService service;
    
    private AgentsModel agents;
    
    private final ModelFunctionFactory factory;
    
    @Inject
    public AgentList(AgentService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }
    
    public AgentsModel getAgents() {
        if (agents == null) {
            agents = factory.agentsToModel().apply(service.findAll());
        }
        return agents;
    }
    
    public String deleteAction(AgentsModel.Agent agent) {
        service.delete(agent.getId());
        return "agent_list?faces-redirect=true";
    }

}
