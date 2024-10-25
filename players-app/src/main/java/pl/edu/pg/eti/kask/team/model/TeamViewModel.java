package pl.edu.pg.eti.kask.team.model;

import lombok.*;
import pl.edu.pg.eti.kask.player.model.PlayersModel;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class TeamViewModel {
    private UUID id;
    private String name;
    private int budget;
    private boolean isProfessional;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Player {

        private UUID id;
        private String name;

    }

    @Singular
    private List<Player> players;
}
