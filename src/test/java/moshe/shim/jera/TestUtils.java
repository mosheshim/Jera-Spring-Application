package moshe.shim.jera;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.entities.Tea;
import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.entities.Weight;
import moshe.shim.jera.payload.WeightDTO;
import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.payload.TeaDTO;
import moshe.shim.jera.payload.TeaProductSeriesDTO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public abstract class TestUtils<T> {

    protected final Class<T> type;

    protected final String path;

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected MockMvc mockMvc;

    protected TestUtils(Class<T> type, String url) {
        this.type = type;
        this.path = url;

    }

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected String asString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    protected T getObjFromResponse(MockHttpServletResponse response)
            throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(response.getContentAsString(), type);
    }

    protected MockHttpServletResponse postRequest(T dto) throws Exception {
        return postRequest(dto, "");
    }

    protected MockHttpServletResponse postRequest(T dto, String endPoint) throws Exception {
        return mockMvc.perform(
                        post(path + endPoint)
                                .content(asString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    protected MockHttpServletResponse getRequest(String url) throws Exception {
        return mockMvc
                .perform(get(url)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    protected MockHttpServletResponse getRequest() throws Exception {
        return getRequest(path);
    }

    protected MockHttpServletResponse putRequest(T dto, long id) throws Exception {
        return mockMvc
                .perform(put(String.format("%s/%d", path, id))
                        .content(asString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }


    protected MockHttpServletResponse deleteRequest(long id) throws Exception {
        return mockMvc
                .perform(delete(String.format("%s/%d", path, id))
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


}
