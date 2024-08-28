package com.simayuygur.atmprojectspringboot.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
public class AdminDto {
    private Integer id;
    private String name;
    private String password;
    private Double amountTotal;  //default zero
}
