package com.simayuygur.atmprojectspringboot.business.service;

import com.simayuygur.atmprojectspringboot.business.UserDto;
import com.simayuygur.atmprojectspringboot.database.entity.UserEntity;
import com.simayuygur.atmprojectspringboot.database.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserServicesInterface userServices;

    @Autowired
    private PasswordEncoder passwordEncoder;


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

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws Throwable {
        UserDto userDto = userServices.getUserById(id);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<?> withdrawMoney(@PathVariable Long id, @RequestParam Long amount) throws Throwable {
        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().body(Map.of("message", "Amount must be greater than zero."));
        }

        UserDto userDto = userServices.getUserById(id);
        if (userDto != null) {
            boolean success = userServices.withdrawMoney(userDto.getId(), amount);
            if (success) {
                Long newBalance = (long) (userDto.getAmount() - amount);
                return ResponseEntity.ok(Map.of("newBalance", newBalance));
            } else {
                return ResponseEntity.status(402).body(Map.of("message", "Insufficient funds."));
            }
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> depositMoney(@PathVariable Long id, @RequestParam Long amount) throws Throwable {

        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().body(Map.of("message", "Amount must be greater than zero."));
        }

        UserDto userDto = userServices.getUserById(id);
        if (userDto != null) {
            userServices.depositMoney(userDto.getId(), amount);
            Long newBalance = (long) (userDto.getAmount() + amount);
            return ResponseEntity.ok(Map.of("newBalance", newBalance));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/sendMoney")
    public ResponseEntity<?> sendMoney(@PathVariable Long id, @RequestParam String iban, @RequestParam Long amount  ) throws Throwable {

        if (amount <= 0) {
            return ResponseEntity.badRequest().body(Map.of("message", "Amount must be greater than zero."));
        }

        if (iban == null || iban.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "IBAN must be provided."));
        }

        Optional<UserDto> fromUserDto = Optional.ofNullable(userServices.getUserById(id));
        Optional<UserDto> toUserDto = Optional.ofNullable(userServices.getUserByIban(iban));

        if (fromUserDto.isPresent() && toUserDto.isPresent()) {
            boolean success = userServices.withdrawMoney(fromUserDto.get().getId(), amount);
            if (success) {
                userServices.depositMoney(toUserDto.get().getId(), amount);
                return ResponseEntity.ok(Map.of("newBalance", fromUserDto.get().getAmount() - amount));
            } else {
                return ResponseEntity.status(402).body(Map.of("message", "Insufficient funds."));
            }
        }

        return ResponseEntity.status(404).body(Map.of("message", "Sender or recipient not found."));
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


/*



    @Autowired
    private UserAuthenticationService userAuthService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        try {
            String token = userAuthService.authenticateUser(userDto);
            return ResponseEntity.ok(Map.of("token", token, "message", "Login successful!"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
        }
    }
 */