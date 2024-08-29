package com.simayuygur.atmprojectspringboot.business;

import com.simayuygur.atmprojectspringboot.database.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
public class UserDto extends BaseEntity implements Serializable {

    private Long id;
    private String name;
    private String password;
    private Double amount;
    private Integer adminId;
    private String ibanNo;

}

