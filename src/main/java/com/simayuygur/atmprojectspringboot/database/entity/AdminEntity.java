package com.simayuygur.atmprojectspringboot.database.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@NoArgsConstructor
@Builder
@Log4j2
@Entity
@Table(name = "admins")
public class AdminEntity extends BaseEntity implements UserDetails {

    @Column(name = "name", nullable = false , unique = true)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "amount_total", nullable = false , columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double amountTotal =  0.0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "admin")
    private Set<UserEntity> users;

    @Column(name = "role", columnDefinition = "ROLE_ADMIN")
    private String role = "ROLE_ADMIN";

    public AdminEntity(String name, String password) {
        this.name = name;
        this.password = password;
        this.amountTotal = 0.0;
    }

    public AdminEntity(String name, String password,Double amountTotal, Set<UserEntity> users, String role) {
        this.name = name;
        this.password = password;
        this.amountTotal = amountTotal != null ? amountTotal : 0.0;
        this.users = users != null ? users : new HashSet<>();
        this.role = "ROLE_ADMIN";
    }

    //buna bakim
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
