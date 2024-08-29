package com.simayuygur.atmprojectspringboot.database.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@Log4j2
@Entity
@Table(name = "admins")
public class AdminEntity extends BaseEntity{

    @Column(name = "name", nullable = false , unique = true)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "amount_total", nullable = false)
    private Double amountTotal = 0.0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admin")
    private Set<UserEntity> users;

    public AdminEntity(String name, String password, Double amountTotal, Set<UserEntity> users) {
        this.name = name;
        this.password = password;
        this.amountTotal = amountTotal;
        this.users = users;
    }
}
