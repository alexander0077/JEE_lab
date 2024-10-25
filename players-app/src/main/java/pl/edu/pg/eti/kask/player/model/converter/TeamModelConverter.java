package pl.edu.pg.eti.kask.player.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.team.entity.Team;
import pl.edu.pg.eti.kask.player.model.TeamModel;
import pl.edu.pg.eti.kask.team.service.TeamService;

import java.util.Optional;
import java.util.UUID;


@FacesConverter(forClass = TeamModel.class, managed = true)
public class TeamModelConverter implements Converter<TeamModel> {

    private final TeamService service;
    private final ModelFunctionFactory factory;

    @Inject
    public TeamModelConverter(TeamService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public TeamModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Team> team = service.find(UUID.fromString(value));
        return team.map(factory.teamToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, TeamModel value) {
        return value == null ? "" : value.getId().toString();
    }

}
