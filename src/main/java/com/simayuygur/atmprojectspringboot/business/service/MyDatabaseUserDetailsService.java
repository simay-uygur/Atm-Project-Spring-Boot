package com.simayuygur.atmprojectspringboot.business.service;

import com.simayuygur.atmprojectspringboot.business.AdminDto;
import com.simayuygur.atmprojectspringboot.business.UserDto;
import com.simayuygur.atmprojectspringboot.business.service.impl.AdminServiceImpl;
import com.simayuygur.atmprojectspringboot.business.service.impl.UserServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class MyDatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AdminServiceImpl adminService;

    /*


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = null;
        AdminDto admin = null;

        try {
            user = userService.findByUserName(username);
        } catch (Throwable e) {
            log.error("User not found with username: {}", username, e);
        }

        if (user == null) {
            try {
                admin = adminService.getAdminByUserName(username);
            } catch (Throwable e) {
                log.error("Admin not found with username: {}", username, e);
            }
        }

        if (user == null && admin == null) {
            throw new UsernameNotFoundException("User or Admin not found with username: " + username);
        }

        // Use a single authority for both types
        List<SimpleGrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails userDetails = null;
        if (user != null) {
            userDetails = new org.springframework.security.core.userdetails.User(
                    user.getName(), user.getPassword(), grantedAuthorities);
        } else if (admin != null) {
            userDetails = new org.springframework.security.core.userdetails.User(
                    admin.getName(), admin.getPassword(), grantedAuthorities);
        }

        log.info("UserDetails: {}", userDetails);
        return userDetails;
    }
  */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = null;
        AdminDto admin = null;

        try {
            user = userService.findByUserName(username);
        } catch (Exception e) {
            log.error("Exception while retrieving user: {}", e.getMessage(), e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        if (user == null) {
            try {
                admin = adminService.getAdminByUserName(username);
            } catch (Exception e) {
                log.error("Exception while retrieving admin: {}", e.getMessage(), e);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        if (user == null && admin == null) {
            log.error("User or Admin not found with username: {}", username);
            throw new UsernameNotFoundException("User or Admin not found with username: " + username);
        }

        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (user != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        if (admin != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        UserDetails userDetails = null;
        if (user != null) {
            userDetails = new org.springframework.security.core.userdetails.User(
                    user.getName(), user.getPassword(), grantedAuthorities);
        } else if (admin != null) {
            userDetails = new org.springframework.security.core.userdetails.User(
                    admin.getName(), admin.getPassword(), grantedAuthorities);
        }

        log.info("UserDetails: {}", userDetails);
        return userDetails;
    }
}
