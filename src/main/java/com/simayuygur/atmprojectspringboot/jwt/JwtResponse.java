package com.simayuygur.atmprojectspringboot.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor //ben koydum
@NoArgsConstructor
public class JwtResponse implements Serializable {

    @Serial  //ide offered
    private static final long serialVersionUID = -809187909192404844L;


    private String token;
    private String username;
    private Long id;
    private String userType;


}
