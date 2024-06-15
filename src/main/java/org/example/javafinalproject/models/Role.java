package org.example.javafinalproject.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.javafinalproject.enums.ERole;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole role;

    public Role() {}

    public Role(ERole role) {
        this.role = role;
    }
}
