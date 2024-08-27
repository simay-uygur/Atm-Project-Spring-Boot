package database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    //bu create için ıolurdu postmappingi o yüzden kullanıyoruz
//    @PostMapping("/loginUser")
//    public void loginUser(@RequestBody User user) {
//
//        log.info("Fetching user with ID: {}", user.getId());
//        try {
//
//        } catch (Exception e) {
//            log.error("An error occurred while fetching user with ID: {}", , e);
//        }
//    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable Long id) {
        log.info("Received request to get user with ID: {}", id);
        try {

            String user = "User" + id;
            log.debug("User found: {}", user);
            return user;
        } catch (Exception e) {
            log.error("Error fetching user with ID: {}", id, e);
            return "Error fetching user";
        }
    }
}