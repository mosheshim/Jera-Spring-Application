package moshe.shim.jera.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import moshe.shim.jera.exceptions.TokenException;
import moshe.shim.jera.payload.SignUpDTO;
import moshe.shim.jera.payload.UserDTO;
import moshe.shim.jera.security.JWTAuthentication;
import moshe.shim.jera.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class JWTTests {

    @Autowired
    private Environment environment;

    @Autowired
    private JWTAuthentication jwtAuth;

    @Autowired
    private UserService userService;

    private UserDTO createUser() {
        return userService.registerUser(
                new SignUpDTO(
                        "email@email.com",
                        "user name",
                        "123456"
                ));
    }

    private String createJWT(Date expires, String email) {
        return JWT.create()
                .withExpiresAt(expires)
                .withSubject(email)
                .sign(Algorithm.HMAC512(Objects.requireNonNull(
                        environment.getProperty("shim.mosh.jwt.secret"))));
    }

    @Test
    public void validateJwt_whenTokenIsExpired_throwTokenException() {
        var user = createUser();
        var jwt = createJWT(
                new Date(System.currentTimeMillis() - 1000),
                user.getEmail()
        );
        var bearerToken = "Bearer " + jwt;
        assertThrows(TokenException.class, () -> jwtAuth.validateJWT(bearerToken));
    }

    @Test
    public void validateJwt_whenTokenIsNotInvalid_receiveEmptyString() {
        System.out.println(jwtAuth.validateJWT("invalid JWT"));
        assertThat(jwtAuth.validateJWT("5")).isEmpty();
    }

    @Test
    public void validateJwt_whenJwtIsValid_receiveNotEmptyString() {
        var user = createUser();
        var jwt = createJWT(
                new Date(System.currentTimeMillis() + 1000),
                user.getEmail()
        );
        var bearerToken = "Bearer " + jwt;
        assertThat(jwtAuth.validateJWT(bearerToken)).isNotEmpty();
    }


}


