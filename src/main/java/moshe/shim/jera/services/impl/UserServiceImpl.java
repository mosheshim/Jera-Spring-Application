package moshe.shim.jera.services.impl;

import lombok.val;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import moshe.shim.jera.entities.Address;
import moshe.shim.jera.entities.Role;
import moshe.shim.jera.entities.User;
import moshe.shim.jera.exceptions.ResourceNotFoundException;
import moshe.shim.jera.exceptions.TokenException;
import moshe.shim.jera.exceptions.UserExistsException;
import moshe.shim.jera.payload.AddressDTO;
import moshe.shim.jera.payload.LoginDTO;
import moshe.shim.jera.payload.SignUpDTO;
import moshe.shim.jera.payload.UserDTO;
import moshe.shim.jera.repositories.RoleRepository;
import moshe.shim.jera.repositories.UserRepository;
import moshe.shim.jera.security.JWTAuthentication;
import moshe.shim.jera.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static moshe.shim.jera.controllers.UserController.API_1_USERS;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTAuthentication jwtAuth;
    private final TypeMap<User, UserDTO> toUserDTO;
    private final TypeMap<UserDTO, User> toUser;
    private final TypeMap<AddressDTO, Address> toAddress;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            ModelMapper modelMapper,
            JWTAuthentication jwtAuth) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuth = jwtAuth;

        this.toUserDTO = modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(mapper -> mapper.using(
                                ctx -> jwtAuth.buildJWT(((String) ctx.getSource())))
                        .map(User::getEmail, UserDTO::setJwt));

        this.toUser = modelMapper.createTypeMap(UserDTO.class, User.class);

        this.toAddress = modelMapper.createTypeMap(AddressDTO.class, Address.class);
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
                .uploadDate(new Date())
                .lastLogin(new Date())
                .roles(getUserRole()) //TODO change to USER
                .build();

        return toUserDTO.map(userRepository.save(user));
    }

    private List<Role> getUserRole() { //TODO make as enum
        var roles = new ArrayList<Role>();
        roles.add(roleRepository.findByName(Role.ADMIN).orElseGet(() ->
                roleRepository.save(Role.builder().name(Role.ADMIN).build())));
        return roles;
    }

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) {
        val email = loginDTO.getEmail();
        val user = findUserByEmail(email);

        val token = new UsernamePasswordAuthenticationToken(
                email, loginDTO.getPassword());

        val authenticate = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        user.setLastLogin(new Date());
        userRepository.save(user);

        return toUserDTO.map(user);
    }

    @Override
    public String updateUser(String bearerToken, UserDTO dto) {
        String email = jwtAuth.validateJWT(bearerToken)
                .orElseThrow(() -> new TokenException(bearerToken, "Invalid"));

        var user = findUserByEmail(email);

        user.setPhone(dto.getPhone());
        user.setName(dto.getName());
        if (dto.getAddress() != null) user.setAddress(toAddress.map(dto.getAddress()));
        userRepository.save(user);

        return "Updated successfully";
    }


    private User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User", "Email", email, API_1_USERS));
    }

    private User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User", "Id", id, API_1_USERS));
    }

}
