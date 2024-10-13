package pl.edu.pg.eti.kask.team.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.player.entity.Player;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Team implements Serializable {
    private UUID id;
    private String name;
    private int budget;
    private boolean isProfessional;
    private List<Player> players;
}