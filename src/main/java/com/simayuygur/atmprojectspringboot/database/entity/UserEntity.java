package com.simayuygur.atmprojectspringboot.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@Builder
@Log4j2
@Entity
@Table(name = "customers")
public class UserEntity extends BaseEntity{



   @Column(name = "name", nullable = false , unique = true) //for login i made it unique
   private String name;

   @Column(name = "password", nullable = false)
   private String password;

   @Column(name = "amount", nullable = false)
   private Double amount = 0.0;

   @Column(name = "admin_id", nullable = false)
   private Integer adminId;

   public UserEntity(String name, String password,Double amount,  Integer adminId) {
      this.amount = amount;
      this.name = name;
      this.password = password;
      this.adminId = adminId;
   }

}
