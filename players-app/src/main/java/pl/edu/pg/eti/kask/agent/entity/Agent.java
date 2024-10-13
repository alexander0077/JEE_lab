package pl.edu.pg.eti.kask.agent.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import pl.edu.pg.eti.kask.player.entity.Player;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Agent implements Serializable {

    private UUID id;
    private String login;
    @ToString.Exclude
    private String password;
    private String email;

    private String name;
    private String surname;
    private int age;
    private boolean active;
    private List<Player> players;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String portrait;
}
