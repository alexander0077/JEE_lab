package pl.edu.pg.eti.kask.agent.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "Agents")
public class Agent implements Serializable {
    @Id
    private UUID id;
    private String login;
    @ToString.Exclude
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;
    private String surname;
    private int age;
    private boolean active;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "agent", cascade = CascadeType.REMOVE)
    private List<Player> players;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String portrait;

    @CollectionTable(name = "agents__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
