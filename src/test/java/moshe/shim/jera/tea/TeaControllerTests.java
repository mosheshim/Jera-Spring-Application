package moshe.shim.jera.tea;

import moshe.shim.jera.payload.WeightDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Date;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TeaControllerTests extends TeaTestsUtils {

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenRoleIsAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = postRequest(createValidTeaDTO());
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void postDTO_whenRoleIsNotAdmin_receiveStatus403() throws Exception {
        MockHttpServletResponse response = postRequest(createValidTeaDTO
                ());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenDTOIsValid_receiveStatus201() throws Exception {
        MockHttpServletResponse response = postRequest(createValidTeaDTO
                ());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenDTOIsValid_receiveDTO() throws Exception {
        MockHttpServletResponse response = postRequest(createValidTeaDTO());
        assertThat(getObjFromResponse(response)).isNotNull();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenDTOWithIdIsPreSet_saveWithNewGenerateId() throws Exception {
        var dto = createValidTeaDTO();
        dto.setId(234L);
        var response = postRequest(dto);
        assertThat(getObjFromResponse(response).getId()).isNotEqualTo(dto.getId());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenNameIsNull_receiveStatus400() throws Exception {
        var dto = createValidTeaDTO();
        dto.setName(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenDescriptionIsNull_receiveStatus400() throws Exception {
        var dto = createValidTeaDTO();
        dto.setDescription(null);
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenImageUrlIsNull_saveDTOWithDefaultImageUrl() throws Exception {
        var dto = createValidTeaDTO();
        dto.setImageUrl(null);
        var response = postRequest(dto);
        assertThat(getObjFromResponse(response).getImageUrl()).isNotEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenUploadDateIsNotNull_saveDTOWithNewGeneratedTimeStamp() throws Exception {
        var dto = createValidTeaDTO();
        dto.setUploadDate(new Date());
        var response = postRequest(dto);
        assertThat(getObjFromResponse(response).getUploadDate()).isNotEqualTo(dto.getUploadDate());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void postDTO_whenWeightsAreNotValid_receiveStatus400() throws Exception {
        var dto = createValidTeaDTO();
        dto.setWeights(List.of(new WeightDTO(null, null, null)));
        var response = postRequest(dto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getAll_receiveListWithItems() throws Exception {
        MockHttpServletResponse response = getRequest();
        assertThat(objectMapper.readValue(
                response.getContentAsString(), List.class))
                .isNotNull();
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
        MockHttpServletResponse response = getRequest(path + "/" + 1);
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getDTOById_whenIdExists_receiveDTO() throws Exception {
        var entity = entityManager.persistAndFlush(createValidTeaEntity());

        MockHttpServletResponse getResponse = getRequest(
                path + "/" + entity.getId());

        var dto = getObjFromResponse(getResponse);

        assertThat(dto).isNotNull();
    }

    @Test
    public void getDTOById_whenIdExists_receiveStatus200() throws Exception {
        var entity = entityManager.persistAndFlush(createValidTeaEntity());

        MockHttpServletResponse getResponse = getRequest(
                path + "/" + entity.getId());

        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getDTOById_whenIdDoesNotExists_receiveStatus400() throws Exception {
        MockHttpServletResponse getResponse = getRequest(path + "/" + 100);
        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void updateEntity_whenUnauthorized_receiveStatus401() throws Exception {
        MockHttpServletResponse response = putRequest(createValidTeaDTO
                (), 1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateEntity_whenRoleIsNotAdmin_receiveStatus403() throws Exception {
        MockHttpServletResponse response = putRequest(createValidTeaDTO(), 1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateEntity_whenRoleIsAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = putRequest(createValidTeaDTO
                (), 1);
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateEntity_whenIdExists_receiveStatus204() throws Exception {
        var entity = entityManager.persistAndFlush(createValidTeaEntity());

        var dto = createValidTeaDTO();
        dto.setName("updated name");
        MockHttpServletResponse putResponse = putRequest(dto, entity.getId());

        assertThat(putResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateEntity_whenIdExists_receiveAString() throws Exception {
        var entity = entityManager.persistAndFlush(createValidTeaEntity());

        var dto = createValidTeaDTO();
        dto.setName("updated name");
        MockHttpServletResponse putResponse = putRequest(dto, entity.getId());

        assertThat(putResponse.getContentAsString()).containsIgnoringWhitespaces("Updated Successfully");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateEntity_whenIdDoesNotExistsInCoffeeTable_receiveStatus404() throws Exception {
        MockHttpServletResponse response = putRequest(createValidTeaDTO(), 100);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void updateWeightEntity_whenUnauthorized_receiveStatus401() throws Exception {
        MockHttpServletResponse response = putRequest(new WeightDTO(10,10,false), 1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateWeightEntity_whenRoleIsNotAdmin_receiveStatus403() throws Exception {
        MockHttpServletResponse response = putRequest(new WeightDTO(10,10,false), 1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateWeightEntity_whenDTOWeightIsValid_receiveStatus400() throws Exception {
        var entity = entityManager.persistAndFlush(createValidTeaEntity());

        var weight = new WeightDTO(entity.getWeights().get(0).getWeight(), 1000, false);

        MockHttpServletResponse putResponse = putRequest(weight, entity.getId());

        assertThat(putResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateWeightEntity_whenDTOWeightIsValid_receiveAString() throws Exception {
        var entity = entityManager.persistAndFlush(createValidTeaEntity());

        var weight = new WeightDTO(entity.getWeights().get(0).getWeight(), 1000, false);

        MockHttpServletResponse putResponse = putRequest(weight, entity.getId());

        assertThat(putResponse.getContentAsString()).isEqualToIgnoringWhitespace("Updated Successfully");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateWeightEntity_whenDTOIsNotValid_receiveStatus400() throws Exception {
        var entity = entityManager.persistAndFlush(createValidTeaEntity());

        var dto = new WeightDTO(entity.getWeights().get(0).getWeight(), null, false);
        MockHttpServletResponse putResponse = putRequest(dto, entity.getId());

        assertThat(putResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateWeightEntity_whenTeaIsNotFound_receiveStatus404() throws Exception {
        var entity = entityManager.persistAndFlush(createValidTeaEntity());

        var dto = new WeightDTO(1, 50, false);
        MockHttpServletResponse putResponse = putRequest(dto, entity.getId());

        assertThat(putResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
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
        var entity = entityManager.persistAndFlush(createValidTeaEntity());

        MockHttpServletResponse deleteResponse = deleteRequest(entity.getId());

        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
