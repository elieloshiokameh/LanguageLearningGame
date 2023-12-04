package com.languagegame.domain;

import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private RoleEnum type;

    public Role(){}
    public Role(RoleEnum type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleEnum getType() {
        return type;
    }

    public void setType(RoleEnum type) {
        this.type = type;
    }
}
