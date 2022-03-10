package moshe.shim.jera.cart_item;

import moshe.shim.jera.entities.CartItem;
import moshe.shim.jera.entities.Role;
import moshe.shim.jera.entities.User;
import moshe.shim.jera.entities.Product;
import moshe.shim.jera.repositories.CartItemRepository;
import moshe.shim.jera.repositories.CoffeeRepository;
import moshe.shim.jera.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.transaction.Transactional;

import java.util.List;

import static moshe.shim.jera.TestsUtils.createValidCoffeeEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestEntityManager
@Transactional
public class CartRepositoryTests {

}
