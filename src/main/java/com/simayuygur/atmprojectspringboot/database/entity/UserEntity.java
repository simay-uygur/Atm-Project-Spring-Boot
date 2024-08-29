package com.simayuygur.atmprojectspringboot.database.entity;

import jakarta.persistence.*;
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

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "admin_id", referencedColumnName = "id")
   private AdminEntity admin;

   @Column(name = "iban_no", nullable = false, unique = true)
   private String ibanNo;

   public UserEntity(String name, String password,Double amount, AdminEntity admin, String ibanNo) {
      this.amount = amount;
      this.name = name;
      this.password = password;
      this.admin = admin;
      this.ibanNo = ibanNo;
   }

}
