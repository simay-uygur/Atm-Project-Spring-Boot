package com.simayuygur.atmprojectspringboot.config;

import com.simayuygur.atmprojectspringboot.bean.PasswordEncoderBean;
import com.simayuygur.atmprojectspringboot.business.service.impl.AdminServiceImpl;
import com.simayuygur.atmprojectspringboot.business.service.impl.UserServiceImpl;
import com.simayuygur.atmprojectspringboot.jwt.JwtAuthenticationEntryPoint;
import com.simayuygur.atmprojectspringboot.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig implements WebSecurityCustomizer {


    @Autowired
    private JwtRequestFilter jwtRequestFilter; // Autowire the JwtRequestFilter

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private PasswordEncoderBean passwordEncoderBean;

    @Autowired
    private UserServiceImpl userDetailsService;  // Your custom service for users

    @Autowired
    private AdminServiceImpl adminDetailsService;  // Your custom service for admins

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public AuthenticationProvider userAuthenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService); // Set your custom user service
//        authProvider.setPasswordEncoder(passwordEncoderBean.passwordEncoder());
//        return authProvider;
//    }
//



    @Override
    public void customize(WebSecurity web) {
        web.ignoring().requestMatchers("/swagger-ui/**",
                "/v3/api-docs/**",
                "/asm-swagger.html",
                "/api-docs/**",
                "/api-docs/swagger-config",
                "/asm-swagger"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)// Disable CSRF since JWT is used
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/user/**").hasRole("USER")
                        .requestMatchers("/api/v1/authenticate").permitAll()
                        .requestMatchers("/api/v1/login","/api/v1/customers/register", "/api/v1/admins/register", "/api/v1/customers/authenticate").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session for JWT
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint) // Custom entry point for unauthorized requests
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Add the unified filter

        ;
//        http.addFilterBefore(jwtAdminFilter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(jwtUserFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




    //i saw it in a website

}
