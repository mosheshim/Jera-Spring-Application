package moshe.shim.jera.user;

import moshe.shim.jera.exceptions.ResourceNotFoundException;
import moshe.shim.jera.exceptions.TokenException;
import moshe.shim.jera.exceptions.UserExistsException;
import moshe.shim.jera.payload.LoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserServiceTests extends UserUtils {


    @Test
    public void saveUser_whenEntityIsValid_haveUserInDB() {
        registerUser();
        assertThat(userRepository.findUserByEmail(email)).isNotNull();
    }

    @Test
    public void saveUser_whenEmailAlreadyExists_throwUserExistsException() {
        registerUser();
        assertThrows(UserExistsException.class, this::registerUser);
    }

    @Test
    public void saveEntity_createTimeStampOnSave_receiveDTOWithUploadDate(){
        var userDTO = registerUser();
        var user = userRepository.findUserByEmail(userDTO.getEmail()).get();
        assertThat(user.getUploadDate()).isNotNull();
        assertThat(user.getLastLogin()).isNotNull();
    }

    @Test
    public void loginUser_whenUserIsSaved_receiveUserDTOWithJWT() {
        registerUser();

        var userDTO = userService.loginUser(new LoginDTO(email, password));

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getJwt()).isNotNull();
    }

    @Test
    public void loginUser_whenEmailIsIncorrect_throwResourceNotFoundException() {
        var validSignUpDTO = createValidSignUpDTO();
        userService.registerUser(validSignUpDTO);
        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.loginUser(new LoginDTO(
                        "notrealemail@email.com", validSignUpDTO.getPassword())));
    }

    @Test
    public void loginUser_whenPasswordIsIncorrect_throwBadCredentialsException() {
        var validSignUpDTO = createValidSignUpDTO();
        userService.registerUser(validSignUpDTO);
        assertThrows(
                BadCredentialsException.class,
                () -> userService.loginUser(new LoginDTO(
                        email, "notrealpassword")));
    }

    @Test
    public void loginUser_whenUserLoggedIn_updateLastLoginField(){
        registerUser();
        var user = userRepository.findUserByEmail(email).orElse(null);
        assert user !=null;

        var firstLogin = user.getLastLogin();
        userService.loginUser(new LoginDTO(email, password));

        assertThat(userRepository
                .findUserByEmail(email).get()
                .getLastLogin())
                .isNotEqualTo(firstLogin);
    }

    @Test
    public void updateUser_whenUserUpdated_receiveString() {
        var userDTO = registerUser();
        var webJwt = addBearer(userDTO.getJwt());

        assertThat(userService.updateUser(webJwt, userDTO)).isEqualTo("Updated successfully");
    }

    @Test
    public void updateUser_whenJWTIsNotValid_throwTokenException() {
        var userDTO = registerUser();
        var webJwt = addBearer(userDTO.getJwt() + "k");
        assertThrows(TokenException.class,
                () -> userService.updateUser(webJwt, userDTO));
    }

    @Test
    public void updateUser_whenJWTIsValid_updateUserInDB(){
        var userDTO = registerUser();
        userDTO.setPhone("064555455");
        userService.updateUser(
                addBearer(userDTO.getJwt()),
                userDTO);

        assertThat(userRepository.findUserByEmail(userDTO.getEmail()).get()
                .getPhone()).isEqualTo(userDTO.getPhone());
    }

    @Test
    public void updateUser_whenAddressIsValid_updateUserAddress(){
        var userDTO = registerUser();
        userDTO.setAddress(createValidAddressDTO());
        userService.updateUser(
                addBearer(userDTO.getJwt()),
                userDTO);

        assertThat(userRepository.findUserByEmail(userDTO.getEmail()).get()
                .getAddress()).isNotNull();
    }

}
