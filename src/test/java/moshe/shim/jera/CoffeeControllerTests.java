package moshe.shim.jera;

import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.repositories.CoffeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CoffeeControllerTests {

    public static final String API_1_COFFEE = "/api/1/coffee";
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    CoffeeRepository coffeeRepository;

    public <T> ResponseEntity<T> postCoffee(
            Object request,
            Class<T> responseType) {
        return testRestTemplate.postForEntity(API_1_COFFEE, request, responseType);
    }


    @BeforeEach
    private void cleanup() {
        coffeeRepository.deleteAll();
    }

    @Test
    public void contextLoads() {
        assertThat(coffeeRepository).isNotNull();
        assertThat(testRestTemplate).isNotNull();
    }

    @Test
    public void postCoffee_whenCoffeeIsValid_receiveStatusOkAndGetCoffeeDTO() {
        ResponseEntity<CoffeeDTO> response = postCoffee(createValidCoffee(), CoffeeDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(CoffeeDTO.class);
        assertThat(coffeeRepository.count()).isEqualTo(1);
    }

    @Test
    public void postCoffee_whenIdIsSetByJson_ignoreJsonId() {
        var coffee = createValidCoffee();
        coffee.setId(234);
        var response = postCoffee(coffee, Object.class);
        assertThat(coffeeRepository.findAll().get(0).getId()).isNotEqualTo(coffee.getId());
    }

    @Test
    public void postCoffee_whenBitternessRatingHasNumberUnderZero_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setBitterness(-1);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenSweetnessRatingHasNumberUnderZero_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setSweetness(-1);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenBodyRatingHasNumberUnderZero_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setBody(-1);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenAcidityRatingHasNumberUnderZero_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setAcidity(-1);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    public void postCoffee_whenBitternessRatingHasNumberBiggerThanFive_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setBitterness(6);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenSweetnessRatingHasNumberBiggerThanFive_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setSweetness(6);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenBodyRatingHasNumberBiggerThanFive_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setBody(6);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenAcidityRatingHasNumberBiggerThanFive_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setAcidity(6);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenRoastingRatingHasNumberBiggerThanThree_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setRoastingLevel(4);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenRoastingRatingHasNumberUnderOne_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setRoastingLevel(0);
        var response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    public void postCoffee_whenImageUrlIsNotSetDefaultUrl() {
        var coffeeWithNullUrl = Coffee.builder()
                .price(50)
                .name("yummy coffe")
                .inStock(true)
                .description("this is coffe")
                .countryOfOrigin("Ugands")
                .roastingLevel(2)
                .bitterness(4)
                .sweetness(3)
                .acidity(2)
                .body(5)
                .tasteProfile("honey, cacoa")
                .build();
        postCoffee(coffeeWithNullUrl, Object.class);
        assertThat(coffeeRepository.findAll().get(0).getImageUrl()).isNotNull();
    }

    @Test
    public void postCoffee_whenNameFieldIsNull_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setName(null);
        ResponseEntity<Object> response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenTasteProfileFieldIsNull_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setTasteProfile(null);
        ResponseEntity<Object> response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenDescriptionFieldIsNull_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setDescription(null);
        ResponseEntity<Object> response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenBodyFieldIsNull_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setBody(null);
        ResponseEntity<Object> response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenAcidityFieldIsNull_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setAcidity(null);
        ResponseEntity<Object> response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenSweetnessFieldIsNull_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setSweetness(null);
        ResponseEntity<Object> response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenBitternessFieldIsNull_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setBitterness(null);
        ResponseEntity<Object> response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postCoffee_whenRoastingLevelFieldIsNull_receiveBadRequest() {
        var coffee = createValidCoffee();
        coffee.setRoastingLevel(null);
        ResponseEntity<Object> response = postCoffee(coffee, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getCoffee_whenDatabaseHasCoffee_receiveListWithItems(){
       postCoffee(createValidCoffee(), CoffeeDTO.class);
        ResponseEntity<List> getResponse = testRestTemplate.getForEntity(API_1_COFFEE, List.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().size()).isGreaterThan(0);
    }

    @Test
    public void getCoffee_ifIdIsFoundInCoffeeTable_receiveCoffeeDTO(){
        ResponseEntity<CoffeeDTO> postResponse = postCoffee(createValidCoffee(), CoffeeDTO.class);
        ResponseEntity<CoffeeDTO> getResponse = testRestTemplate.getForEntity(
                (API_1_COFFEE +"/"+ postResponse.getBody().getId()), CoffeeDTO.class);

        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getId()).isEqualTo(postResponse.getBody().getId());
    }

    @Test
    public void getCoffee_ifIdIsNotFoundInCoffeeTable_receiveNotFound() {
        ResponseEntity<CoffeeDTO> getResponse = testRestTemplate.getForEntity(
                (API_1_COFFEE +"/"+ 1), CoffeeDTO.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void putCoffee_ifIdIsFoundInCoffeeTable_updatedCoffeeDTO(){
        ResponseEntity<CoffeeDTO> postResponse = postCoffee(createValidCoffee(), CoffeeDTO.class);
        postResponse.getBody().setName("updated name");

        HttpEntity<CoffeeDTO> requestEntity = new HttpEntity<>(postResponse.getBody());

        ResponseEntity<CoffeeDTO> getResponse = testRestTemplate.exchange(
                (API_1_COFFEE +"/"+ postResponse.getBody().getId()),
                HttpMethod.PUT,
                requestEntity,
                CoffeeDTO.class);
        assertThat(getResponse.getBody().getName()).isEqualTo("updated name");
    }

    @Test
    public void putCoffee_ifIdIsNotFoundInCoffeeTable_receiveNotFound(){

        HttpEntity<CoffeeDTO> requestEntity = new HttpEntity<>(createValidCoffee());

        ResponseEntity<CoffeeDTO> getResponse = testRestTemplate.exchange(
                (API_1_COFFEE +"/"+ 1),
                HttpMethod.PUT,
                requestEntity,
                CoffeeDTO.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteCoffee_ifIdIsFoundInCoffeeTable_coffeeWontBeInDatabase(){
        ResponseEntity<CoffeeDTO> postResponse = postCoffee(createValidCoffee(), CoffeeDTO.class);

        testRestTemplate.delete(
                API_1_COFFEE +"/"+ postResponse.getBody().getId());

        ResponseEntity<CoffeeDTO> getResponse = testRestTemplate.getForEntity(
                (API_1_COFFEE +"/"+ postResponse.getBody().getId()), CoffeeDTO.class);

        assertThat(getResponse.getBody()).isNotEqualTo(postResponse.getBody());
    }


    public CoffeeDTO createValidCoffee() {
       return CoffeeDTO.builder()
                .price(50)
                .name("yummy coffe")
                .imageUrl("image")
                .inStock(true)
                .description("this is coffe")
                .countryOfOrigin("Ugands")
                .roastingLevel(2)
                .bitterness(4)
                .sweetness(3)
                .acidity(2)
                .body(5)
                .tasteProfile("honey, cacoa")
                .build();
    }
}
