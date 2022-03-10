package moshe.shim.jera.services;

import moshe.shim.jera.payload.AddressDTO;
import moshe.shim.jera.payload.LoginDTO;
import moshe.shim.jera.payload.SignUpDTO;
import moshe.shim.jera.payload.UserDTO;

public interface UserService {
    UserDTO registerUser(SignUpDTO signUpDTO);
    UserDTO loginUser(LoginDTO loginDTO);
//    UserDTO addAddress(String jwtFromHeader, AddressDTO dto);
    String updateUser(String bearerToken,UserDTO dto);
}
