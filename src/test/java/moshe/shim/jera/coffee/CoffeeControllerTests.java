package moshe.shim.jera.coffee;

import moshe.shim.jera.TestUtils;
import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.repositories.CoffeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WithMockUser(roles = "ADMIN")
public class CoffeeControllerTests extends TestUtils<CoffeeDTO> {


    @Autowired
    CoffeeRepository coffeeRepository;


    public CoffeeControllerTests() {
        super(CoffeeDTO.class, "/api/1/coffee");
    }

    @BeforeEach
    private void cleanup() {
        coffeeRepository.deleteAll();
    }


    @Override
    protected CoffeeDTO createValidDTO() {
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

    @Test
    public void contextLoads() {
        assertThat(coffeeRepository).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(coffeeRepository).isNotNull();
    }


    @Test
    public void postDTO_whenRoleIsAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = postRequest(createValidDTO());
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void postDTO_whenRoleIsNotAdmin_receiveStatus403() throws Exception {
        MockHttpServletResponse response = postRequest(createValidDTO());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void postDTO_whenDTOIsValid_receiveStatus201() throws Exception {
        MockHttpServletResponse response = postRequest(createValidDTO());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void postDTO_whenDTOIsValid_receiveDTO() throws Exception {
        MockHttpServletResponse response = postRequest(createValidDTO());
        assertThat(getObjFromResponse(response)).isNotNull();
    }

    @Test
    public void postDTO_whenDTOWithIdIsPreSet_saveWithNewGenerateId() throws Exception {
        var dto = createValidDTO();
        dto.setId(234L);
        var response = postRequest(dto);
        assertThat(getObjFromResponse(response).getId()).isNotEqualTo(dto.getId());
    }

    @Test
    public void postDTO_whenBitternessRatingHasNumberUnderZero_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setBitterness(-1);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenSweetnessRatingHasNumberUnderZero_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setSweetness(-1);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenBodyRatingHasNumberUnderZero_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setBody(-1);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenAcidityRatingHasNumberUnderZero_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setAcidity(-1);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void postDTO_whenBitternessRatingHasNumberBiggerThanFive_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setBitterness(6);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenSweetnessRatingHasNumberBiggerThanFive_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setSweetness(6);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenBodyRatingHasNumberBiggerThanFive_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setBody(6);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenAcidityRatingHasNumberBiggerThanFive_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setAcidity(6);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenRoastingRatingHasNumberBiggerThanThree_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setRoastingLevel(4);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenRoastingRatingHasNumberUnderOne_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setRoastingLevel(0);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenNameIsNull_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setName(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenTasteProfileIsNull_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setTasteProfile(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenDescriptionIsNull_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setDescription(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenBodyIsNull_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setBody(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenAcidityIsNull_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setAcidity(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenSweetnessIsNull_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setSweetness(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenBitternessIsNull_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setBitterness(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenRoastingLevelIsNull_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        dto.setRoastingLevel(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void postDTO_whenImageUrlIsNull_saveDTOWithDefaultImageUrl() throws Exception {
        var dtoWithNullUrl = createValidDTO();
        dtoWithNullUrl.setImageUrl(null);
        var response = postRequest(dtoWithNullUrl);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(getObjFromResponse(response).getImageUrl()).isNotEmpty();
    }

    @Test
    public void getDTOList_receiveListWithItems() throws Exception {
        postRequest(createValidDTO());
        MockHttpServletResponse response = getRequest();

        assertThat(objectMapper.readValue(
                response.getContentAsString(), List.class))
                .isNotNull();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getDTO_whenRoleIsNotAdmin_receiveStatus200() throws Exception {
        MockHttpServletResponse response = getRequest();
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getDTOById_whenRoleIsNotAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = getRequest(
                path + "/" + 1);
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getDTOById_whenIdExists_receiveDTO() throws Exception {
        MockHttpServletResponse postResponse = postRequest(createValidDTO());

        MockHttpServletResponse getResponse = getRequest(
                path + "/" + getObjFromResponse(postResponse).getId());

        CoffeeDTO dto = getObjFromResponse(getResponse);

        assertThat(dto).isNotNull();
    }

    @Test
    public void getDTOById_whenIdExists_receiveStatus200() throws Exception {
        MockHttpServletResponse postResponse = postRequest(createValidDTO());

        MockHttpServletResponse getResponse = getRequest(
                path + "/" + getObjFromResponse(postResponse).getId());

        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getDTOById_whenIdDoesNotExists_receiveStatus400() throws Exception {
        MockHttpServletResponse getResponse = getRequest(path + "/" + 100);
        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateEntity_whenRoleIsNotAdmin_receiveStatus403() throws Exception {
        MockHttpServletResponse response = putRequest(createValidDTO(), 1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void updateEntity_whenRoleIsAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = putRequest(createValidDTO(), 1);
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void updateEntity_whenIdExists_updatedCoffeeInDB() throws Exception {
        var dto = createValidDTO();
        MockHttpServletResponse postResponse = postRequest(dto);
        var dtoId = getObjFromResponse(postResponse).getId();

        dto.setName("updated name");
        MockHttpServletResponse putResponse = putRequest(dto, dtoId);

        var dtoByID = coffeeRepository.findById(dtoId).orElse(null);

        assertThat(putResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(dtoByID).isNotNull();
        assertThat(dtoByID.getName()).isEqualTo(dto.getName());
    }

    @Test
    public void updateEntity_whenIdDoesNotExistsInCoffeeTable_receiveStatus404() throws Exception {
        MockHttpServletResponse response = putRequest(createValidDTO(), 100);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void updateEntity_whenCoffeeIsNotValid_receiveStatus400() throws Exception {
        var dto = createValidDTO();
        MockHttpServletResponse postResponse = postRequest(dto);
        var dtoId = getObjFromResponse(postResponse).getId();

        dto = new CoffeeDTO();
        MockHttpServletResponse putResponse = putRequest(dto, dtoId);

        assertThat(putResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deleteEntity_whenRoleIsAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = deleteRequest(1);
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void deleteEntity_whenRoleIsNotAdmin_receiveStatus403() throws Exception {
        MockHttpServletResponse response = deleteRequest(1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void deleteEntity_whenIdExists_entityWontBeInDatabase() throws Exception {
        MockHttpServletResponse postResponse = postRequest(createValidDTO());

        MockHttpServletResponse deleteResponse = deleteRequest(getObjFromResponse(postResponse).getId());

        MockHttpServletResponse getResponse = getRequest(
                path + "/" + getObjFromResponse(postResponse).getId());

        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


}
