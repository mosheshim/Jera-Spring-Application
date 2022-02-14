package moshe.shim.jera;

import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.repositories.CoffeeRepository;
import moshe.shim.jera.exceptions.APIErrorMessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.linesOf;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CoffeeControllerTests {

    public static final String API_1_COFFEE = "/api/1/coffee";
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    CoffeeRepository coffeeRepo;

    public <T> ResponseEntity<T> postCoffee(
            Object request,
            Class<T> responseType) {
        return testRestTemplate.postForEntity(API_1_COFFEE, request, responseType);
    }

    @BeforeEach
    private void cleanup() {
        coffeeRepo.deleteAll();
    }

    @Test
    public void contextLoads() {
        assertThat(coffeeRepo).isNotNull();
        assertThat(testRestTemplate).isNotNull();
    }

    @Test
    public void postCoffee_whenCoffeeIsValid_receiveStatusOk() {
        Coffee coffee = createValidCoffee();
        ResponseEntity<Object> response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(coffeeRepo.count()).isEqualTo(1);
    }

    @Test
    public void postCoffee_whenIdIsNotNull_receiveBadRequest(){
        var coffee = createValidCoffee();
        coffee.setId(234);
        var response = postCoffee(coffee, Object.class);
        assertThat(coffeeRepo.findById(coffee.getId())).isEmpty();
    }

    @Test
    public void postCoffee_whenBitternessRatingHasNumberUnderZero_receiveBadRequest(){
        var coffee = createValidCoffee();
        coffee.setBitterness(-1);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postCoffee_whenSweetnessRatingHasNumberUnderZero_receiveBadRequest(){
        var coffee = createValidCoffee();
        coffee.setSweetness(-1);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postCoffee_whenBodyRatingHasNumberUnderZero_receiveBadRequest(){
        var coffee = createValidCoffee();
        coffee.setBody(-1);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postCoffee_whenAcidityRatingHasNumberUnderZero_receiveBadRequest(){
        var coffee = createValidCoffee();
        coffee.setAcidity(-1);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    public void postCoffee_whenBitternessRatingHasNumberBiggerThanFive_receiveBadRequest(){
        var coffee = createValidCoffee();
        coffee.setBitterness(6);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postCoffee_whenSweetnessRatingHasNumberBiggerThanFive_receiveBadRequest(){
        var coffee = createValidCoffee();
        coffee.setSweetness(6);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postCoffee_whenBodyRatingHasNumberBiggerThanFive_receiveBadRequest(){
        var coffee = createValidCoffee();
        coffee.setBody(6);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    public void postCoffee_whenAcidityRatingHasNumberBiggerThanFive_receiveBadRequest(){
        var coffee = createValidCoffee();
        coffee.setAcidity(6);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenRoastingRatingHasNumberBiggerThanFive_receiveBadRequest(){
        var coffee = createValidCoffee();
        coffee.setRoastingLevel(6);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenRoastingRatingHasNumberUnderZero_receiveBadRequest(){
        var coffee = createValidCoffee();
        coffee.setRoastingLevel(-1);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    public void postCoffee_whenImageUrlIsNullSetDefaultUrl() {
        var coffeeWithNullUrl = new Coffee();
        coffeeWithNullUrl.setPrice(10);
        coffeeWithNullUrl.setName("name");
        coffeeWithNullUrl.setInStock(false);
        coffeeWithNullUrl.setCountryOfOrigin("country");
        coffeeWithNullUrl.setTasteProfile("tastes");
        coffeeWithNullUrl.setDescription("description");
        coffeeWithNullUrl.setBitterness(5);
        coffeeWithNullUrl.setSweetness(3);
        coffeeWithNullUrl.setAcidity(1);
        coffeeWithNullUrl.setBody(4);
        coffeeWithNullUrl.setRoastingLevel(2);
        postCoffee(coffeeWithNullUrl, Object.class);
        assertThat(coffeeRepo.findAll().get(0).getImageUrl()).isNotNull();
    }

    @Test
    public void postCoffee_whenFieldIsNull_receiveApiError(){
        var coffee = createValidCoffee();
        coffee.setName(null);
        ResponseEntity<APIErrorMessageDTO> response = postCoffee(coffee, APIErrorMessageDTO.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFailedURL()).isEqualTo("/api/1/coffee");
    }


    public Coffee createValidCoffee() {
        Coffee coffee = new Coffee();
        coffee.setPrice(50);
        coffee.setName("yummy coffe");
        coffee.setImageUrl("image");
        coffee.setInStock(true);
        coffee.setDescription("this is coffe");
        coffee.setCountryOfOrigin("Ugands");
        coffee.setRoastingLevel(2);
        coffee.setBitterness(4);
        coffee.setSweetness(3);
        coffee.setAcidity(2);
        coffee.setBody(5);
        coffee.setTasteProfile("honey, cacoa");
        return coffee;
    }
}
