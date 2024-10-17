package pl.edu.pg.eti.kask.player.controller.api;

import pl.edu.pg.eti.kask.player.dto.GetPlayerResponse;
import pl.edu.pg.eti.kask.player.dto.GetPlayersResponse;
import pl.edu.pg.eti.kask.player.dto.PatchPlayerRequest;
import pl.edu.pg.eti.kask.player.dto.PutPlayerRequest;

import java.util.UUID;

public interface PlayerController {
    GetPlayersResponse getPlayers();
     
    GetPlayersResponse getTeamPlayers(UUID id);

    GetPlayerResponse getPlayer(UUID uuid);
    
    void putPlayer(UUID id, PutPlayerRequest request);

    void patchPlayer(UUID id, PatchPlayerRequest request);

    void deletePlayer(UUID id);
    
}
