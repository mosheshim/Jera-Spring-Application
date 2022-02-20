package moshe.shim.jera.services;

import moshe.shim.jera.entities.Role;
import moshe.shim.jera.payload.JWTWebTokenDTO;
import moshe.shim.jera.payload.LoginDTO;
import moshe.shim.jera.payload.SignUpDTO;
import moshe.shim.jera.payload.UserDTO;

public interface AuthService {
    UserDTO registerUser(SignUpDTO signUpDTO);
    UserDTO loginUser(LoginDTO loginDTO);

}
