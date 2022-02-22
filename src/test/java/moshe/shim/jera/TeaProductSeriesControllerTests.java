package moshe.shim.jera;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import moshe.shim.jera.controllers.TeaProductSeriesController;
import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.payload.TeaProductSeriesDTO;
import moshe.shim.jera.repositories.CoffeeRepository;
import moshe.shim.jera.repositories.TeaProductSeriesRepository;
import moshe.shim.jera.services.AuthService;
import moshe.shim.jera.services.TeaProductSeriesService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;
import java.net.http.HttpClient;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@WithMockUser(roles = "ADMIN")
public class TeaProductSeriesControllerTests extends TestUtils<TeaProductSeriesDTO> {

    @Autowired
    TeaProductSeriesRepository PSRepository;

    protected TeaProductSeriesControllerTests() {
        super(TeaProductSeriesDTO.class, "/api/1/product-series");
    }

    @BeforeEach
    private void cleanup(){
        PSRepository.deleteAll();
    }

    @Override
    protected TeaProductSeriesDTO createValidDTO() {
        return TeaProductSeriesDTO.builder()
                .isTeaBag(true)
                .description("product series description")
                .name("product series name")
                .prices("30 - 50")
                .imageUrl("product series image url")
                .build();
    }

    @Test
    public void postPS_whenUserIsAdmin_receiveStatus403() throws  Exception{
        MockHttpServletResponse response = postRequest(createValidDTO());
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void postPS_whenUserIsNotAdmin_receiveStatus403() throws  Exception{
        MockHttpServletResponse response = postRequest(createValidDTO());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void postPS_whenPDIsValid_receiveStatus201() throws  Exception{
        MockHttpServletResponse response = postRequest(createValidDTO());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(getObjFromResponse(response)).hasNoNullFieldsOrPropertiesExcept("teaList");
    }

    @Test
    public void postPS_whenPDIsNotValid_receiveStatus400() throws  Exception{
        MockHttpServletResponse response = postRequest(new TeaProductSeriesDTO());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postPS_whenPDWithIdIsPreSet_saveWithNewGenerateId() throws Exception {
        var ps = createValidDTO();
        ps.setId(234L);
        var response = postRequest(ps);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(getObjFromResponse(response).getId()).isNotEqualTo(ps.getId());
    }

    @Test
    public void postPS_whenNameIsNull_receiveStatus400() throws  Exception{
        TeaProductSeriesDTO validDTO = createValidDTO();
        validDTO.setName(null);
        MockHttpServletResponse response = postRequest(validDTO);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postPS_whenDescriptionIsNull_receiveStatus400() throws  Exception{
        TeaProductSeriesDTO validDTO = createValidDTO();
        validDTO.setDescription(null);
        MockHttpServletResponse response = postRequest(validDTO);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postPS_whenPricesIsNull_receiveStatus400() throws  Exception{
        TeaProductSeriesDTO validDTO = createValidDTO();
        validDTO.setPrices(null);
        MockHttpServletResponse response = postRequest(validDTO);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postPS_whenIsTeaBagIsNotSet_receiveStatus201() throws  Exception{
        TeaProductSeriesDTO validDTO = TeaProductSeriesDTO.builder()
                .description("description")
                .name("name")
                .prices("20-30")
                .imageUrl("image url")
                .build();
        MockHttpServletResponse response = postRequest(validDTO);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(getObjFromResponse(response).isTeaBag()).isFalse();
    }

    @Test
    public void postPS_whenImageUrlIsNotSet_savePSWithDefaultImageUrl() throws Exception {
        var pdWithNullUrl = TeaProductSeriesDTO.builder()
                .description("description")
                .name("name")
                .prices("20-30")
                .build();
        var response = postRequest(pdWithNullUrl);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(getObjFromResponse(response).getImageUrl()).isNotEmpty();
    }

    @Test
    public void getPSList_whenServerIsWorking_receiveListWithItems() throws Exception {
        postRequest(createValidDTO());
        MockHttpServletResponse response = getRequest();

        assertThat(objectMapper.readValue(
                response.getContentAsString(), List.class))
                .isNotNull();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getPS_whenRoleIsNotAdmin_receiveStatus200() throws Exception {
        MockHttpServletResponse response = getRequest();

        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getPSById_whenRoleIsNotAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = getRequest(
                path + "/" + 1);
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getCoffeeById_whenIdExists_receiveCoffeeDTO() throws Exception {
        MockHttpServletResponse postResponse = postRequest(createValidDTO());

        MockHttpServletResponse getResponse = getRequest(
                path + "/" + getObjFromResponse(postResponse).getId());

        var ps = getObjFromResponse(getResponse);

        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(ps).isNotNull();
    }


}
