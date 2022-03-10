package moshe.shim.jera.user;

import moshe.shim.jera.entities.User;
import moshe.shim.jera.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestEntityManager
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findById_whenEmailExists_receiveUser(){
        var email = "email@email.com";
        entityManager.persistAndFlush(User.builder()
                .email(email)
                .name("name")
                .password("password")
                .build());
        assertThat(userRepository.findUserByEmail(email)).isNotEmpty();
    }
    @Test

    public void findById_whenEmailNotExists_receiveNull(){
        var email = "email@email.com";
        assertThat(userRepository.findUserByEmail(email)).isEmpty();
    }

}
