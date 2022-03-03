package moshe.shim.jera.tea_product_series;

import moshe.shim.jera.TestUtils;
import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.payload.TeaProductSeriesDTO;
import moshe.shim.jera.repositories.TeaProductSeriesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Date;
import java.util.List;

import static moshe.shim.jera.controllers.TeaProductSeriesController.API_1_PRODUCT_SERIES;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TeaProductSeriesControllerTests extends TestUtils<TeaProductSeriesDTO> {


    protected TeaProductSeriesControllerTests() {
        super(TeaProductSeriesDTO.class, API_1_PRODUCT_SERIES);
    }

    private final String ADMIN = "ADMIN";
    private final String USER = "USER";


    @Test
    @WithMockUser(roles = ADMIN)
    public void postPS_whenUserIsAdmin_dontReceiveStatus403() throws  Exception{
        MockHttpServletResponse response = postRequest(createValidPSDTO());
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void postPS_whenUnauthorized_receiveStatus401() throws  Exception{
        MockHttpServletResponse response = postRequest(createValidPSDTO());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @WithMockUser(roles = USER)
    public void postPS_whenUserIsNotAdmin_receiveStatus403() throws  Exception {
        MockHttpServletResponse response = postRequest(createValidPSDTO());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void postPS_whenPDIsValid_receiveStatus201() throws  Exception{
        MockHttpServletResponse response = postRequest(createValidPSDTO());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(getObjFromResponse(response)).hasNoNullFieldsOrPropertiesExcept("teaList");
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void postPS_whenPDIsNotValid_receiveStatus400() throws  Exception{
        MockHttpServletResponse response = postRequest(new TeaProductSeriesDTO());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void postPS_whenPDWithIdIsPreSet_saveWithNewGenerateId() throws Exception {
        var ps = createValidPSDTO();
        ps.setId(234L);
        var response = postRequest(ps);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(getObjFromResponse(response).getId()).isNotEqualTo(ps.getId());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void postPS_whenNameIsNull_receiveStatus400() throws  Exception{
        TeaProductSeriesDTO validDTO = createValidPSDTO();
        validDTO.setName(null);
        MockHttpServletResponse response = postRequest(validDTO);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void postPS_whenDescriptionIsNull_receiveStatus400() throws  Exception{
        TeaProductSeriesDTO validDTO = createValidPSDTO();
        validDTO.setDescription(null);
        MockHttpServletResponse response = postRequest(validDTO);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void postPS_whenPricesIsNull_receiveStatus400() throws  Exception{
        TeaProductSeriesDTO validDTO = createValidPSDTO();
        validDTO.setPrices(null);
        MockHttpServletResponse response = postRequest(validDTO);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void postPS_whenIsTeaBagIsNull_receiveStatus201() throws  Exception{
        var validDTO = createValidPSDTO();
        validDTO.setIsTeaBag(null);
        MockHttpServletResponse response = postRequest(validDTO);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void postDTO_whenUploadDateIsNotNull_saveDTOWithNewGeneratedTimeStamp() throws Exception {
        var dto = createValidPSDTO();
        dto.setUploadDate(new Date());
        var response = postRequest(dto);
        assertThat(getObjFromResponse(response).getUploadDate()).isNotEqualTo(dto.getUploadDate());
    }

    @Test
    public void getDTOList_whenServerIsWorking_receiveListWithItems() throws Exception {
        MockHttpServletResponse response = getRequest();

        assertThat(objectMapper.readValue(
                response.getContentAsString(), List.class))
                .isNotNull();
    }

    @Test
    public void getDTOList_whenServerIsWorking_receiveStatus200() throws Exception {
        MockHttpServletResponse response = getRequest();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getDTOById_whenRoleIsUnauthorized_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = getRequest(
                path + "/" + 1);
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void getDTOById_whenIdExists_receiveDTO() throws Exception {
        var psEntity = entityManager.persistAndFlush(createValidPSEntity());

        MockHttpServletResponse getResponse = getRequest(
                path + "/" + psEntity.getId());

        var psDTO = getObjFromResponse(getResponse);

        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(psDTO).isNotNull();
    }

    @Test
    public void getDTOById_whenIdExists_receiveStatus200() throws Exception {
        var ps = entityManager.persistAndFlush(createValidPSEntity());


        MockHttpServletResponse getResponse = getRequest(
                path + "/" + ps.getId());

        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getDTOById_whenIdDoesNotExists_receiveStatus404() throws Exception {
        MockHttpServletResponse getResponse = getRequest(path + "/" + 100);
        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void updateEntity_whenUnauthorized_receiveStatus401() throws Exception {
        MockHttpServletResponse response = putRequest(createValidPSDTO(), 1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @WithMockUser(roles = USER)
    public void updateEntity_whenRoleIsNotAdmin_receiveStatus403() throws Exception {
        MockHttpServletResponse response = putRequest(createValidPSDTO(), 1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void updateEntity_whenRoleIsAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = putRequest(createValidPSDTO(), 1);
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void updateEntity_whenIdExists_receiveStatus204() throws Exception {
        var dto = createValidPSDTO();
        MockHttpServletResponse postResponse = postRequest(dto);
        var dtoId = getObjFromResponse(postResponse).getId();

        dto.setName("updated name");
        MockHttpServletResponse putResponse = putRequest(dto, dtoId);

        assertThat(putResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void updateEntity_whenIdDoesNotExistsInTable_receiveStatus404() throws Exception {
        MockHttpServletResponse response = putRequest(createValidPSDTO(), 100);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void updateEntity_whenDTOIsNotValid_receiveStatus400() throws Exception {
        var dto = createValidPSDTO();
        MockHttpServletResponse postResponse = postRequest(dto);
        var dtoId = getObjFromResponse(postResponse).getId();

        dto = new TeaProductSeriesDTO();
        MockHttpServletResponse putResponse = putRequest(dto, dtoId);

        assertThat(putResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deleteEntity_whenUnauthorized_receiveStatus401() throws Exception {
        MockHttpServletResponse response = deleteRequest(1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void deleteEntity_whenRoleIsAdmin_dontReceiveStatus403() throws Exception {
        MockHttpServletResponse response = deleteRequest(1);
        assertThat(response.getStatus()).isNotEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = USER)
    public void deleteEntity_whenRoleIsNotAdmin_receiveStatus403() throws Exception {
        MockHttpServletResponse response = deleteRequest(1);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void deleteEntity_whenEntityWasDeleted_receiveAString() throws Exception {
        MockHttpServletResponse postResponse = postRequest(createValidPSDTO());
        var response = deleteRequest(getObjFromResponse(postResponse).getId());

        assertThat(response.getContentAsString()).containsIgnoringWhitespaces("Deleted Successfully");
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void deleteEntity_whenIdDoesNotExists_receiveStatus404() throws Exception {
        MockHttpServletResponse deleteResponse = deleteRequest(100);

        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithMockUser(roles = ADMIN)
    public void deleteEntity_whenIdExists_receiveStatus204() throws Exception {
        MockHttpServletResponse postResponse = postRequest(createValidPSDTO());

        MockHttpServletResponse deleteResponse = deleteRequest(getObjFromResponse(postResponse).getId());

        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}
