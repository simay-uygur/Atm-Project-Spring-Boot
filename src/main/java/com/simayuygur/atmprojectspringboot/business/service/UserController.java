package com.simayuygur.atmprojectspringboot.business.service;

import com.simayuygur.atmprojectspringboot.business.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserServicesInterface userServices;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        boolean isAuthenticated = userServices.authenticateUser(userDto.getName(), userDto.getPassword());

        if (isAuthenticated) {
            Long userId = null;
            try {
                userId = userServices.getUserIdByUsername(userDto.getName());
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok(Map.of("message", "Login successful!", "adminId", userId));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password."));
        }

    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userServices.createUser(userDto);
        return ResponseEntity.status(201).body(createdUser);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws Throwable {
        UserDto userDto = userServices.getUserById(id);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws Throwable {
        UserDto updatedUser = userServices.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) throws Throwable {
        userServices.deleteUser(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() throws Throwable { //if it is empty?
        List<UserDto> users = userServices.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/selections/{id}/allCustomers")
    public ResponseEntity<List<UserDto>> getAllCustomers(@PathVariable Integer id) throws Throwable {
        List<UserDto> users = userServices.getUsersWithSameAdmin(id.longValue());
        return ResponseEntity.ok(users);
    }
}
