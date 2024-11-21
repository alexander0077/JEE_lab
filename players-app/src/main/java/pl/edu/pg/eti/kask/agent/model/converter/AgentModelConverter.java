package pl.edu.pg.eti.kask.agent.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.agent.model.AgentModel;
import pl.edu.pg.eti.kask.agent.service.AgentService;
import pl.edu.pg.eti.kask.component.ModelFunctionFactory;

import java.util.Optional;

@FacesConverter(forClass = AgentModel.class, managed = true)
public class AgentModelConverter implements Converter<AgentModel> {
    
    private final AgentService service;
    private final ModelFunctionFactory factory;

    
    @Inject
    public AgentModelConverter(AgentService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public AgentModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Agent> agent = service.find(value);
        return agent.map(factory.agentToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, AgentModel value) {
        return value == null ? "" : value.getLogin();
    }
}

