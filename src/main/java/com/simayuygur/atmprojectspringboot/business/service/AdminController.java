package com.simayuygur.atmprojectspringboot.business.service;

import com.simayuygur.atmprojectspringboot.business.AdminDto;
import com.simayuygur.atmprojectspringboot.business.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    @Autowired
    @Qualifier("adminServiceImpl")
    private AdminServicesInterface adminServices;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserServicesInterface userServices;


//    @PostMapping("/login")
//    public ResponseEntity<?> loginAdmin(@RequestBody AdminDto adminDto) {
//        boolean isAuthenticated = adminServices.authenticateAdmin(adminDto.getName(), adminDto.getPassword());
//        if (isAuthenticated) {
//            Long adminId = null;
//            try {
//                adminId = adminServices.getAdminIdByUsername(adminDto.getName());
//            } catch (Throwable e) {
//                throw new RuntimeException(e);
//            }
//            return ResponseEntity.ok(Map.of("message", "Login successful!", "adminId", adminId));
//        } else {
//            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password."));
//        }
//    }

//    @PostMapping
//    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto) {
//        AdminDto createdAdmin = adminServices.createAdmin(adminDto);
//        return ResponseEntity.status(201).body(createdAdmin);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Long id) throws Throwable {
        AdminDto userDto = adminServices.getAdminById(id);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDto> updateAdmin(@PathVariable Long id, @RequestBody AdminDto userDto) throws Throwable {
        AdminDto updatedAdmin = adminServices.updateAdmin(id, userDto);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteAdmin(@PathVariable Long id) throws Throwable {
        adminServices.deleteAdmin(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    //doesnt work
    @GetMapping ("/{id}/allCustomers")
    public ResponseEntity<List<UserDto>> getAllCustomers(@PathVariable Long id) {
        List<UserDto> users = userServices.getUsersWithSameAdmin(id);
        return ResponseEntity.ok(users);
    }

}

    
