package moshe.shim.jera.services.impl;

import lombok.val;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import moshe.shim.jera.entities.Role;
import moshe.shim.jera.entities.User;
import moshe.shim.jera.exceptions.UserExistsException;
import moshe.shim.jera.payload.LoginDTO;
import moshe.shim.jera.payload.SignUpDTO;
import moshe.shim.jera.payload.UserDTO;
import moshe.shim.jera.repositories.RoleRepository;
import moshe.shim.jera.repositories.UserRepository;
import moshe.shim.jera.security.SecurityConfiguration;
import moshe.shim.jera.services.AuthService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.Destination;
import java.util.Collections;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final Environment environment;
    private final TypeMap<User, UserDTO> toUserDTO;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager, Environment environment, PasswordEncoder passwordEncoder,
            ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.environment = environment;
        this.passwordEncoder = passwordEncoder;
        this.toUserDTO = modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(mapper -> mapper.using(
                        ctx -> buildJWT(((String) ctx.getSource())))
                        .map(User::getEmail, UserDTO::setJwt));
    }


    @Override
    public UserDTO registerUser(SignUpDTO signUpDTO) {
        if (userRepository.findUserByEmail(signUpDTO.getEmail()).isPresent())
            throw new UserExistsException(signUpDTO.getEmail() + " Already exists");

        val encodedPassword = passwordEncoder.encode(signUpDTO.getPassword());
        val user = User.builder()
                .email(signUpDTO.getEmail())
                .name(signUpDTO.getName())
                .password(encodedPassword)
                .roles(Collections.singleton(getUserRole(Role.ROLE_USER)))
                .build();
        return toUserDTO.map(userRepository.save(user));
    }

    private Role getUserRole(String role) { //TODO make as enum
        return roleRepository.findByName(role).orElseGet(() ->
                roleRepository.save(Role.builder().name(role).build()));
    }

    private String buildJWT(String email) {
        val expires = environment.getProperty("shim.mosh.jwt.expires", Long.class);
        val secret = environment.getProperty("shim.mosh.jwt.secret");
        if (expires == null || secret == null)
            throw new NullPointerException("Secret JWT key or JWT Expires setting is not in properties");

        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + expires) )
                .withSubject(email)
                .sign(Algorithm.HMAC512(secret));

    }

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) {
        val email = loginDTO.getEmail();
        val user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with the email: " + email));

        val token = new UsernamePasswordAuthenticationToken(
                email,
                loginDTO.getPassword());

        val authenticate = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return toUserDTO.map(user);
    }
}
