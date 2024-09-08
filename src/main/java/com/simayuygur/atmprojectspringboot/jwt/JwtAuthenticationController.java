package com.simayuygur.atmprojectspringboot.jwt;

import com.simayuygur.atmprojectspringboot.business.AdminDto;
import com.simayuygur.atmprojectspringboot.business.UserDto;
import com.simayuygur.atmprojectspringboot.business.service.impl.AdminServiceImpl;
import com.simayuygur.atmprojectspringboot.business.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
public class JwtAuthenticationController {

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private AdminServiceImpl adminDetailsService;

    @RequestMapping(value = "/customers/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
        return ResponseEntity.ok(userDetailsService.createUser(user));
    }

    @RequestMapping(value = "/admins/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveAdmin(@RequestBody AdminDto user) throws Exception {
        return ResponseEntity.ok(adminDetailsService.createAdmin(user));
    }

}

