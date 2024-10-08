package com.simayuygur.atmprojectspringboot.database.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@Log4j2
@Entity
@Table(name = "customers")
public class UserEntity extends BaseEntity implements UserDetails {

   @Column(name = "name", nullable = false , unique = true) //for login i made it unique
   private String name;

   @Column(name = "password", nullable = false)
   private String password;

   @Column(name = "amount", nullable = false)
   private Double amount = 0.0;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "admin_id", referencedColumnName = "id")
   private AdminEntity admin;

   @Column(name = "iban_no", nullable = false, unique = true)
   private String ibanNo;

   @Column(name = "role", columnDefinition = "ROLE_USER")
   private String role;


   public UserEntity(String name, String password,Double amount, AdminEntity admin, String ibanNo, String role) {
      this.amount = amount;
      this.name = name;
      this.password = password;
      this.admin = admin;
      this.ibanNo = ibanNo;
      this.role = role;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
   }

   @Override
   public String getUsername() {
      return name;
   }

   @Override
   public boolean isAccountNonExpired() {
      return UserDetails.super.isAccountNonExpired();
   }

   @Override
   public boolean isAccountNonLocked() {
      return UserDetails.super.isAccountNonLocked();
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return UserDetails.super.isCredentialsNonExpired();
   }

   @Override
   public boolean isEnabled() {
      return UserDetails.super.isEnabled();
   }
}
