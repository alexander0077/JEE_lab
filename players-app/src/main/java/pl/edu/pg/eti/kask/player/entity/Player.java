package pl.edu.pg.eti.kask.player.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.team.entity.Team;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Player implements Serializable {
    private UUID id;
    private String name;
    private String surname;
    private int shirtNumber;
    private PositionTypes position;
    private Team team;
    private Agent agent;
}
