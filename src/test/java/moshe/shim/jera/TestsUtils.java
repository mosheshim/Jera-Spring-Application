package moshe.shim.jera;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.entities.Tea;
import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.entities.Weight;
import moshe.shim.jera.payload.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public abstract class TestsUtils<T> {

    protected final String prefix = "/api/1/";

    protected final Class<T> type;

    protected final String defaultEndPoint;
    @Autowired
    protected TestEntityManager entityManager;
    @Autowired
    protected  MockMvc mockMvc;

    protected TestsUtils(Class<T> type, String defaultEndPoint) {
        this.type = type;
        this.defaultEndPoint = defaultEndPoint;
    }

    protected String addBearer(String jwt){
        return "Bearer " + jwt;
    }

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected String asString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    protected T getObjFromResponse(MockHttpServletResponse response)
            throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(response.getContentAsString(), type);
    }

    protected MockHttpServletResponse postRequest(Object dto) throws Exception {
        return postRequest(dto, defaultEndPoint);
    }

    protected MockHttpServletResponse postRequest(Object dto, String endPoint) throws Exception {
        return mockMvc.perform(
                        post(prefix + endPoint)
                                .content(asString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    protected MockHttpServletResponse getRequest(String endPoint) throws Exception {
        return mockMvc
                .perform(get(prefix + endPoint)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    protected MockHttpServletResponse getRequest(Object dto, String endPoint) throws Exception {
        return mockMvc
                .perform(get(prefix + endPoint)
                        .content(asString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    protected MockHttpServletResponse getRequest() throws Exception {
        return getRequest(defaultEndPoint);
    }

    protected MockHttpServletResponse putRequest(Object dto, long id) throws Exception {
        return putRequest(dto, defaultEndPoint + "/" + id);
    }

    protected MockHttpServletResponse putRequest(Object dto, String endPoint) throws Exception {
        return mockMvc
                .perform(put(prefix + endPoint)
                        .content(asString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }


    protected MockHttpServletResponse deleteRequest(long id) throws Exception {

        return deleteRequest(defaultEndPoint + "/" + id);
    }

    protected MockHttpServletResponse deleteRequest(String endPoint) throws Exception {
        System.out.println(prefix + endPoint);
        return mockMvc
                .perform(delete(prefix + endPoint)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    public static CoffeeDTO createValidCoffeeDTO() {
        return CoffeeDTO.builder()
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

    public static Coffee createValidCoffeeEntity() {
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

    public static Tea createTeaEntity() {
        return Tea.builder()
                .price(50)
                .name("tea name")
                .imageUrl("tea image url")
                .inStock(true)
                .description("tea description")
                .weights(createValidWeightList())
                .build();
    }

    private static List<Weight> createValidWeightList() {
        var weights = new ArrayList<Weight>();
        for (int i = 0; i < 3; i++) {
            weights.add(Weight.builder()
                    .price(10 * i)
                    .weight(50 * i)
                    .inStock(i < 1)
                    .build());
        }
        return weights;
    }

    public static TeaDTO createValidTeaDTO() {
        return TeaDTO.builder()
                .name("tea name")
                .imageUrl("tea image url")
                .inStock(true)
                .description("tea description")
                .weights(List.of(
                        new WeightDTO(100, 50, true),
                        new WeightDTO(150, 70, true),
                        new WeightDTO(200, 100, false)
                ))
                .build();
    }

    public static TeaProductSeriesDTO createValidPSDTO() {
        return TeaProductSeriesDTO.builder()
                .isTeaBag(true)
                .description("product series description")
                .name("product series name")
                .prices("30 - 50")
                .imageUrl("product series image url")
                .build();
    }


    public static TeaProductSeries createValidPSEntity() {
        return TeaProductSeries.builder()
                .name("product series name")
                .description("product series description")
                .prices("50 - 80")
                .imageUrl("product series image url")
                .isTeaBag(true)
                .build();
    }

    public static String email = "email@email.com";
    public static String password = "Password123!";

    public static SignUpDTO createValidSignUpDTO() {
        return new SignUpDTO(email, "name", password);
    }



}
