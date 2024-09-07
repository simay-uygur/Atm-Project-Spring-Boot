package com.simayuygur.atmprojectspringboot.jwt;

import com.simayuygur.atmprojectspringboot.business.AdminDto;
import com.simayuygur.atmprojectspringboot.business.UserDto;
import com.simayuygur.atmprojectspringboot.business.service.impl.AdminServiceImpl;
import com.simayuygur.atmprojectspringboot.business.service.impl.UserServiceImpl;
import com.simayuygur.atmprojectspringboot.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private AdminServiceImpl adminDetailsService;

    //to register
    //http://localhost:8080/register ==> POST {"username":"Simay","password":"123"}
    @RequestMapping(value = "/customers/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
        return ResponseEntity.ok(userDetailsService.createUser(user));
    }

    @RequestMapping(value = "/admins/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveAdmin(@RequestBody AdminDto user) throws Exception {
        return ResponseEntity.ok(adminDetailsService.createAdmin(user));
    }



//    @RequestMapping(value = "/customers/authenticate", method = RequestMethod.POST)
//    public ResponseEntity<?> createUserAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Throwable {
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//
//        UserDetails user = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//
//        if (user != null) {
//            final String token = jwtTokenUtil.generateToken(user);
//            return ResponseEntity.ok(new JwtResponse(token));
//        } else {
//            return ResponseEntity.status(401).body("Invalid username or password.");
//        }
//    }
//
//    // birleştiremem galiba
//    @RequestMapping(value = "/admins/authenticate", method = RequestMethod.POST)
//    public ResponseEntity<?> createAdminAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Throwable {
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//
//        UserDetails admindetails = adminDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//
//        if (admindetails != null) {
//            final String token = jwtTokenUtil.generateToken(admindetails);
//            return ResponseEntity.ok(new JwtResponse(token));
//        } else {
//            return ResponseEntity.status(401).body("Invalid username or password.");
//        }
//    }
//
//    private void authenticate(String username, String password) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
}

