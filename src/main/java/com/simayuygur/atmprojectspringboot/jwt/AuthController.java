package com.simayuygur.atmprojectspringboot.jwt;

import com.simayuygur.atmprojectspringboot.business.service.MyDatabaseUserDetailsService;
import com.simayuygur.atmprojectspringboot.business.service.impl.UserServiceImpl;
import com.simayuygur.atmprojectspringboot.database.entity.AdminEntity;
import com.simayuygur.atmprojectspringboot.util.JwtTokenUtil;
import io.jsonwebtoken.Jwt;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserServiceImpl userServiceImpl;


    @GetMapping("/api/v1/authenticate")
    public ResponseEntity<AuthValidationResponse> authenticate(@RequestHeader("Authorization") String token) {
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        boolean isValid = jwtTokenUtil.validateToken(jwtToken, userDetails);

        if (isValid) {
            return ResponseEntity.ok(new AuthValidationResponse("Token is valid"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthValidationResponse("Invalid token"));
        }
    }

    @SneakyThrows
    @PostMapping("/api/v1/login")
//    public ResponseEntity<?> login(@RequestBody JwtRequest loginRequest) throws Exception {
//        try {
//            // Authenticate user
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // Generate JWT token
//            //String jwt = jwtTokenUtil.generateToken(authentication);
//
//            // Get user details
//            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
//            final String token = jwtTokenUtil.generateToken(userDetails);
//
//            if ("admin".equals(loginRequest.getUserType())) {
//                // For admin login
//
//                return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), null, "ADMIN"));
//            } else {
//                // For customer login
//                Long customerId = userServiceImpl.getUserIdByUsername(userDetails.getUsername());
//                return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), customerId, "USER"));
//            }
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new Error("Invalid username or password"));
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
//    }
    public JwtResponse login(@RequestBody JwtRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Long id = userServiceImpl.getUserIdByUsername(userDetails.getUsername());

        return new JwtResponse(token,userDetails.getUsername(),id, userDetails.getAuthorities().toString().trim());
    }
}
