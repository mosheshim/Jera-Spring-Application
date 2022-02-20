package moshe.shim.jera.controllers;

import lombok.Getter;
import moshe.shim.jera.entities.Role;
import moshe.shim.jera.payload.JWTWebTokenDTO;
import moshe.shim.jera.payload.LoginDTO;
import moshe.shim.jera.payload.SignUpDTO;
import moshe.shim.jera.payload.UserDTO;
import moshe.shim.jera.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody SignUpDTO signUpDTO){
        return new ResponseEntity<>(authService.registerUser(signUpDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(authService.loginUser(loginDTO));
    }

}
