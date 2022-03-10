package moshe.shim.jera.user;

import moshe.shim.jera.TestsUtils;
import moshe.shim.jera.payload.AddressDTO;
import moshe.shim.jera.payload.SignUpDTO;
import moshe.shim.jera.payload.UserDTO;
import moshe.shim.jera.repositories.UserRepository;
import moshe.shim.jera.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;


public class UserUtils extends TestsUtils<UserDTO> {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserService userService;

    protected final String email = "email@email.com";
    protected final String password = "Password123";


    protected UserUtils() {
        super(UserDTO.class, "users");
    }

    protected String addBearer(String jwt){
        return "Bearer " + jwt;
    }

    protected SignUpDTO createValidSignUpDTO() {
        return new SignUpDTO(email, "name", password);
    }

    protected UserDTO registerUser() {
        return userService.registerUser(new SignUpDTO(
                email,
                "user name",
                password
        ));
    }

    protected AddressDTO createValidAddressDTO() {
        return new AddressDTO(
                "Tel Aviv",
                "Rotchild",
                "50",
                "1234556",
                "5",
                "20",
                "A"
        );
    }


}
