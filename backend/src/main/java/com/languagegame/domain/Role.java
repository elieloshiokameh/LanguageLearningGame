package com.languagegame.domain;

import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //TODO change to Long / Integer

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private RoleEnum type;

    public Role(){}
    public Role(RoleEnum type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoleEnum getType() {
        return type;
    }

    public void setType(RoleEnum type) {
        this.type = type;
    }
}
