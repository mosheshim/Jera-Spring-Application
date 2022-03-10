package moshe.shim.jera.controllers;

import moshe.shim.jera.entities.User;
import moshe.shim.jera.payload.AddressDTO;
import moshe.shim.jera.payload.LoginDTO;
import moshe.shim.jera.payload.SignUpDTO;
import moshe.shim.jera.payload.UserDTO;
import moshe.shim.jera.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import static moshe.shim.jera.controllers.UserController.API_1_USERS;

@RestController
@RequestMapping(API_1_USERS)
public class UserController {
    public final static String API_1_USERS = "/api/1/users";

    private final UserService userService;

    public UserController(UserService authService) {
        this.userService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody SignUpDTO signUpDTO) {
        return new ResponseEntity<>(userService.registerUser(signUpDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateUser(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwtToken,
            @Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUser(jwtToken, userDTO), HttpStatus.NO_CONTENT);
    }


    @GetMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.loginUser(loginDTO));
    }



}
