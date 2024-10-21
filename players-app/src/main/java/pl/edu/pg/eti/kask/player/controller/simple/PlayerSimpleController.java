package pl.edu.pg.eti.kask.player.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.player.controller.api.PlayerController;
import pl.edu.pg.eti.kask.player.dto.GetPlayerResponse;
import pl.edu.pg.eti.kask.player.dto.GetPlayersResponse;
import pl.edu.pg.eti.kask.player.dto.PatchPlayerRequest;
import pl.edu.pg.eti.kask.player.dto.PutPlayerRequest;
import pl.edu.pg.eti.kask.player.service.PlayerService;
import pl.edu.pg.eti.kask.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.controller.servlet.exception.BadRequestException;

import java.util.UUID;

@RequestScoped
public class PlayerSimpleController implements PlayerController {
    private final PlayerService service;
    private final DtoFunctionFactory factory;

    @Inject
    public PlayerSimpleController(PlayerService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetPlayersResponse getPlayers() {
        return factory.playersToResponse().apply(service.findAll());
    }

    @Override
    public GetPlayersResponse getTeamPlayers(UUID id) {
        return service.findAllByTeam(id)
                .map(factory.playersToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetPlayersResponse getAgentPlayers(UUID id) {
        return service.findAllByAgent(id)
                .map(factory.playersToResponse())
                .orElseThrow(NotFoundException::new);
    }


    @Override
    public GetPlayerResponse getPlayer(UUID uuid) {
        return service.find(uuid)
                .map(factory.playerToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putPlayer(UUID id, PutPlayerRequest request) {
        try {
            service.create(factory.requestToPlayer().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchPlayer(UUID id, PatchPlayerRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updatePlayer().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deletePlayer(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
