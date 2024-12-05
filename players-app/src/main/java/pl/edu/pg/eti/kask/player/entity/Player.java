package pl.edu.pg.eti.kask.player.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.agent.entity.Agent;
import pl.edu.pg.eti.kask.entity.VersionAndCreationDateAuditable;
import pl.edu.pg.eti.kask.team.entity.Team;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "players")
public class Player extends VersionAndCreationDateAuditable implements Serializable {
    @Id
    private UUID id;
    private String name;
    private String surname;
    private int shirtNumber;
    private String position;

    @ManyToOne
    @JoinColumn(name = "team")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "agent_name")
    private Agent agent;
}
