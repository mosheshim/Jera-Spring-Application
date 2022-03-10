package moshe.shim.jera.coffee;

import moshe.shim.jera.TestsUtils;
import moshe.shim.jera.payload.CoffeeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoffeeControllerTests extends TestsUtils<CoffeeDTO> {


    public CoffeeControllerTests() {
        super(CoffeeDTO.class, "coffee");
    }

    @Test
    public void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenRoleIsAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = postRequest(createValidCoffeeDTO());
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void postDTO_whenRoleIsNotAdmin_receiveStatus403() throws Exception {
        MockHttpServletResponse response = postRequest(createValidCoffeeDTO());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenDTOIsValid_receiveStatus201() throws Exception {
        MockHttpServletResponse response = postRequest(createValidCoffeeDTO());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenDTOIsValid_receiveDTO() throws Exception {
        MockHttpServletResponse response = postRequest(createValidCoffeeDTO());
        assertThat(getObjFromResponse(response)).isNotNull();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenDTOWithIdIsPreSet_saveWithNewGenerateId() throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setId(234L);
        var response = postRequest(dto);
        assertThat(getObjFromResponse(response).getId()).isNotEqualTo(dto.getId());
    }


    @ParameterizedTest
    @ValueSource(ints = {-1, 6})
    @WithMockUser(roles = "ADMIN")
    void postDTO_whenBitternessRatingIsNotValid_receiveStatus400(int i) throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setBitterness(i);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 6})
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenSweetnessRatingNotValid_receiveStatus400(int i) throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setSweetness(i);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 6})
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenBodyRatingIsNotValid_receiveStatus400(int i) throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setBody(i);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 6})
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenAcidityRatingIsNotValid_receiveStatus400(int i) throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setAcidity(i);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 4})
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenRoastingRatingIsNotValid_receiveStatus400(int i) throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setRoastingLevel(i);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenNameIsNull_receiveStatus400() throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setName(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenTasteProfileIsNull_receiveStatus400() throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setTasteProfile(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenDescriptionIsNull_receiveStatus400() throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setDescription(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenBodyIsNull_receiveStatus400() throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setBody(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenAcidityIsNull_receiveStatus400() throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setAcidity(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenSweetnessIsNull_receiveStatus400() throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setSweetness(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenBitternessIsNull_receiveStatus400() throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setBitterness(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenRoastingLevelIsNull_receiveStatus400() throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setRoastingLevel(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenImageUrlIsNull_saveDTOWithDefaultImageUrl() throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setImageUrl(null);
        var response = postRequest(dto);
        assertThat(getObjFromResponse(response).getImageUrl()).isNotEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenUploadDateIsNotNull_saveDTOWithNewGeneratedTimeStamp() throws Exception {
        var dto = createValidCoffeeDTO();
        dto.setUploadDate(new Date());
        var response = postRequest(dto);
        assertThat(getObjFromResponse(response).getUploadDate()).isNotEqualTo(dto.getUploadDate());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void getDTO_whenRoleIsAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = getRequest();
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getDTO_whenRoleIsNotAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = getRequest();
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getDTOById_whenRoleIsNotAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = getRequest(
                defaultEndPoint + "/" + 1);
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getDTOById_whenIdExists_receiveDTO() throws Exception {
        var entity = entityManager.persistAndFlush(createValidCoffeeEntity());

        MockHttpServletResponse getResponse = getRequest(
                defaultEndPoint + "/" + entity.getId());

        assertThat(getObjFromResponse(getResponse)).isNotNull();
    }

    @Test
    public void getDTOById_whenIdExists_receiveStatus200() throws Exception {
        var entity = entityManager.persistAndFlush(createValidCoffeeEntity());

        MockHttpServletResponse getResponse = getRequest(
                defaultEndPoint + "/" + entity.getId());

        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getDTOById_whenIdDoesNotExists_receiveStatus400() throws Exception {
        MockHttpServletResponse getResponse = getRequest(defaultEndPoint + "/" + 100);
        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void updateEntity_whenUnauthorized_receiveStatus401() throws Exception {
        MockHttpServletResponse response = putRequest(createValidCoffeeDTO(), 1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateEntity_whenRoleIsNotAdmin_receiveStatus403() throws Exception {
        MockHttpServletResponse response = putRequest(createValidCoffeeDTO(), 1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateEntity_whenRoleIsAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = putRequest(createValidCoffeeDTO(), 1);
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateEntity_whenIdExists_receiveStatus204() throws Exception {
        var entity = entityManager.persistAndFlush(createValidCoffeeEntity());

        var dto = createValidCoffeeDTO();
        dto.setName("updated name");
        MockHttpServletResponse putResponse = putRequest(dto, entity.getId());

        assertThat(putResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateEntity_whenIdDoesNotExistsInCoffeeTable_receiveStatus404() throws Exception {
        MockHttpServletResponse response = putRequest(createValidCoffeeDTO(), 100);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateEntity_whenDTOIsNotValid_receiveStatus400() throws Exception {
        var entity = entityManager.persistAndFlush(createValidCoffeeEntity());

        var dto = new CoffeeDTO();
        MockHttpServletResponse putResponse = putRequest(dto, entity.getId());

        assertThat(putResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deleteEntity_whenUnauthorized_receiveStatus401() throws Exception {
        MockHttpServletResponse response = deleteRequest(1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
    @WithMockUser(roles = "ADMIN")
    public void deleteEntity_whenIdDoesNotExists_receiveStatus404() throws Exception {
        MockHttpServletResponse deleteResponse = deleteRequest(100);
        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteEntity_whenIdExists_receiveStatus204() throws Exception {
        var entity = entityManager.persistAndFlush(createValidCoffeeEntity());

        MockHttpServletResponse deleteResponse = deleteRequest(entity.getId());

        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}
