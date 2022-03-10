package moshe.shim.jera.cart_item;

import moshe.shim.jera.TestsUtils;
import moshe.shim.jera.entities.CartItem;
import moshe.shim.jera.entities.Product;
import moshe.shim.jera.entities.Role;
import moshe.shim.jera.entities.User;
import moshe.shim.jera.payload.CartItemDTO;
import moshe.shim.jera.repositories.CartItemRepository;
import moshe.shim.jera.repositories.CoffeeRepository;
import moshe.shim.jera.repositories.UserRepository;
import moshe.shim.jera.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CartTestsUtils extends TestsUtils<CartItemDTO> {

    @Autowired
    protected CartItemRepository cartRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected CoffeeRepository coffeeRepository;
    @Autowired
    protected UserService userService;

    private Product product;

    private User user;

    @BeforeEach
    protected void init(){
        product = entityManager.persistAndFlush(createValidCoffeeEntity());

        user = entityManager.persistAndFlush(User.builder()
                .name("name")
                .email(email)
                .roles(List.of(entityManager.persistAndFlush(
                        Role.builder().name("ROLE_USER").build())))
                .password(password)
                .build());
    }

    protected CartItem createValidCartItemEntity(){
        return CartItem.builder()
                .extra("extra")
                .user(user)
                .amount(1)
                .product(product)
                .build();
    }

    protected CartTestsUtils() {
        super(CartItemDTO.class, "");
    }


}
