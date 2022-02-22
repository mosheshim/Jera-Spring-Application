package moshe.shim.jera.coffee;

import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.repositories.CoffeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import javax.validation.ConstraintViolationException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class CoffeeRepositoryTests {
    @Autowired
    private CoffeeRepository coffeeRepository;


    private Coffee createValidObj() {
        return Coffee.builder()
                .price(50)
                .name("coffee name")
                .imageUrl("coffee image url")
                .inStock(true)
                .description("coffee description")
                .countryOfOrigin("coffee country of origin")
                .roastingLevel(2)
                .bitterness(4)
                .sweetness(3)
                .acidity(2)
                .body(5)
                .tasteProfile("coffee taste profile")
                .build();
    }

    @BeforeEach
    private void cleanup() {
        coffeeRepository.deleteAll();
    }
// TODO change the name get with receive
    @Test
    public void saveEntity_whenObjIsValid_getCoffeeBack() {
        var validObj = createValidObj();
        var saved = coffeeRepository.saveAndFlush(validObj);
        assertThat(saved).isNotNull();
    }

    @Test
    public void saveEntity_whenObjIsValid_findObjInDB() {
        var validObj = createValidObj();
        var saved = coffeeRepository.saveAndFlush(validObj);
        assertThat(coffeeRepository.findById(saved.getId()).orElse(null)).isNotNull();
    }

    @Test()
    public void saveEntity_whenObjNotValid_throwConstraintViolationException() {
        var obj = new Coffee();
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test()
    public void saveEntity_whenUploadDateIsNull_generateUploadDate() {
        var obj = createValidObj();
        obj.setUploadDate(null);
        var saved = coffeeRepository.saveAndFlush(obj);
        assertThat(saved.getUploadDate()).isNotNull();
    }

    @Test()
    public void saveEntity_whenNameIsNull_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setName(null);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test()
    public void saveEntity_whenImageUrlIsNull_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setImageUrl(null);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test()
    public void saveEntity_whenDescriptionIsNull_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setDescription(null);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test()
    public void saveEntity_whenPriceIsNull_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setPrice(null);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test()
    public void saveEntity_whenInStockIsNotSet_setInStockToFalse() {
        Coffee coffee = new Coffee();
        coffee.setBitterness(1);
        coffee.setSweetness(1);
        coffee.setAcidity(1);
        coffee.setBody(1);
        coffee.setRoastingLevel(1);
        coffee.setName("text");
        coffee.setCountryOfOrigin("text");
        coffee.setDescription("text");
        coffee.setImageUrl("text");
        coffee.setTasteProfile("text");
        coffee.setPrice(10);
        Coffee saved = coffeeRepository.saveAndFlush(coffee);
        assertThat(saved.isInStock()).isFalse();
    }

    @Test()
    public void saveEntity_whenCountryOfOriginIsNull_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setCountryOfOrigin(null);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test()
    public void saveEntity_whenTasteProfileIsNull_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setTasteProfile(null);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenBitternessRatingHasNumberUnderZero_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setBitterness(-1);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenSweetnessRatingHasNumberUnderZero_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setSweetness(-1);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenBodyRatingHasNumberUnderZero_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setBody(-1);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenAcidityRatingHasNumberUnderZero_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setAcidity(-1);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }


    @Test
    public void saveCoffee_whenBitternessRatingHasNumberBiggerThanFive_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setBitterness(6);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenSweetnessRatingHasNumberBiggerThanFive_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setSweetness(6);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenBodyRatingHasNumberBiggerThanFive_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setBody(6);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenAcidityRatingHasNumberBiggerThanFive_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setAcidity(6);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenRoastingRatingHasNumberBiggerThanThree_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setRoastingLevel(4);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenRoastingRatingHasNumberUnderOne_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setRoastingLevel(-1);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenBodyFieldIsNull_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setBody(null);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenAcidityFieldIsNull_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setAcidity(null);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenSweetnessFieldIsNull_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setSweetness(null);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenBitternessFieldIsNull_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setBitterness(null);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

    @Test
    public void saveCoffee_whenRoastingLevelFieldIsNull_throwConstraintViolationException() {
        var obj = createValidObj();
        obj.setRoastingLevel(null);
        assertThrows(ConstraintViolationException.class, () -> coffeeRepository.saveAndFlush(obj));
    }

}
