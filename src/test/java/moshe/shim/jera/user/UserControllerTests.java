package moshe.shim.jera.user;

import moshe.shim.jera.payload.AddressDTO;
import moshe.shim.jera.payload.LoginDTO;
import moshe.shim.jera.payload.SignUpDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class UserControllerTests extends UserUtils{

    @Override
    protected MockHttpServletResponse putRequest(Object dto, String jwt) throws Exception {
        jwt = addBearer(jwt);
        return mockMvc.perform(
                        put(prefix + "users")
                                .header(HttpHeaders.AUTHORIZATION, jwt)
                                .content(asString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    private MockHttpServletResponse updateAddress(AddressDTO addressDTO) throws Exception {
        var userDTO = registerUser();
        userDTO.setAddress(addressDTO);
        return putRequest(userDTO, userDTO.getJwt());
    }

    @Test
    public void registerUser_whenSignUpDetailsAreValid_receiveStatus201() throws Exception {
        var response = postRequest(createValidSignUpDTO(), "/users/register");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void registerUser_whenSignUpDetailsAreValid_receiveDTO() throws Exception {
        var response = postRequest(createValidSignUpDTO(), "/users/register");
        assertThat(getObjFromResponse(response)).isNotNull();
    }

    @Test
    public void registerUser_whenSignUpDetailsAreNotValid_receiveStatus400() throws Exception {
        var response = postRequest(new SignUpDTO(), "/users/register");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void registerUser_whenSignUpEmailIsNotValid_receiveStatus400() throws Exception {
        var signUpDTO = createValidSignUpDTO();
        signUpDTO.setEmail("notValidEmail");
        var response = postRequest(signUpDTO, "/users/register");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void registerUser_whenSignUpEmailIsNull_receiveStatus400() throws Exception {
        var signUpDTO = createValidSignUpDTO();
        signUpDTO.setEmail(null);
        var response = postRequest(signUpDTO, "/users/register");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void registerUser_whenSignUpNameIsNull_receiveStatus400() throws Exception {
        var signUpDTO = createValidSignUpDTO();
        signUpDTO.setName(null);
        var response = postRequest(signUpDTO, "/users/register");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void registerUser_whenSignUpNameIsEmpty_receiveStatus400() throws Exception {
        var signUpDTO = createValidSignUpDTO();
        signUpDTO.setName("");
        var response = postRequest(signUpDTO, "/users/register");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void registerUser_whenSignUpPasswordIsNull_receiveStatus400() throws Exception {
        var signUpDTO = createValidSignUpDTO();
        signUpDTO.setPassword(null);
        var response = postRequest(signUpDTO, "/users/register");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void registerUser_whenSignUpPasswordSizeIsLessThan8_receiveStatus400() throws Exception {
        var signUpDTO = createValidSignUpDTO();
        signUpDTO.setPassword("Pardss1");
        var response = postRequest(signUpDTO, "/users/register");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void registerUser_whenSignUpPasswordDoesNotContainUppercaseLetter_receiveStatus400() throws Exception {
        var signUpDTO = createValidSignUpDTO();
        signUpDTO.setPassword("password1");
        var response = postRequest(signUpDTO, "/users/register");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void registerUser_whenSignUpPasswordDoesNotContainLowercaseLetter_receiveStatus400() throws Exception {
        var signUpDTO = createValidSignUpDTO();
        signUpDTO.setPassword("PASSWORD1");
        var response = postRequest(signUpDTO, "/users/register");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void registerUser_whenSignUpPasswordDoesNotContainDigitLetter_receiveStatus400() throws Exception {
        var signUpDTO = createValidSignUpDTO();
        signUpDTO.setPassword("Password");
        var response = postRequest(signUpDTO, "/users/register");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void loginUser_whenUserIsFound_receiveStatus200() throws Exception {
        registerUser();
        var response = getRequest(
                new LoginDTO(email, password),
                "/users/login");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void loginUser_whenUserIsFound_receiveDTO() throws Exception {
        registerUser();
        var response = getRequest(
                new LoginDTO(email, password),
                "/users/login");
        assertThat(getObjFromResponse(response)).isNotNull();
    }

    @Test
    public void loginUser_whenUserIsNotFound_receiveStatus404() throws Exception {
        var response = getRequest(
                new LoginDTO(email, password),
                "/users/login");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void loginUser_whenEmailIsNull_receiveStatus400() throws Exception {
        var response = getRequest(
                new LoginDTO(null, password),
                "/users/login");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void loginUser_whenPasswordIsNull_receiveStatus400() throws Exception {
        var response = getRequest(
                new LoginDTO(email, null),
                "/users/login");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void loginUser_whenPasswordIsIncorrect_receiveStatus401() throws Exception {
        registerUser();
        var response = getRequest(
                new LoginDTO(email, "wrongPassword1"),
                "/users/login");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void updateUser_whenNoJWTPassedInHeaders_receiveStatus400() throws Exception{
        var response = mockMvc.perform(
                        put(prefix + "users")
                                .content(asString(createValidAddressDTO()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressIsValid_receiveStatus201() throws Exception{
        var userDTO = registerUser();
        var response = putRequest(
                createValidAddressDTO(),
                addBearer(userDTO.getJwt()));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void updateUser_whenAddressIsValid_receiveString() throws Exception{
        var userDTO = registerUser();
        var response = putRequest(
                createValidAddressDTO(),
                addBearer(userDTO.getJwt()));
        assertThat(response.getContentAsString()).isEqualToIgnoringWhitespace("Updated successfully");
    }

    @Test
    public void updateUser_whenAddressCitySizeIsOver30_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setCity("a".repeat(31));
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressCityIsEmpty_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setCity("");
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressCityIsNull_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setCity(null);
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressStreetSizeIsOver30_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setStreet("a".repeat(31));
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressStreetIsEmpty_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setStreet("");
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressStreetIsNull_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setStreet(null);
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressHouseNumberSizeIsOver10_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setHouseNumber("a".repeat(11));
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressHouseNumberIsEmpty_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setHouseNumber("");
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressHouseNumberIsNull_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setHouseNumber(null);
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressZipSizeIsOver20_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setZip("a".repeat(21));
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressZipIsEmpty_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setZip("");
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressZipSizeIsNull_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setZip(null);
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressFloorSizeIsOver10_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setFloor("a".repeat(11));
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressFloorIsEmpty_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setFloor("");
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressFloorSizeIsNull_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setFloor(null);
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressApartmentSizeIsOver10_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setApartment("a".repeat(11));
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressApartmentIsEmpty_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setApartment("");
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressApartmentSizeIsNull_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setApartment(null);
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressEntranceSizeIsOver10_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setEntrance("a".repeat(11));
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressEntranceIsEmpty_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setEntrance("");
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenAddressEntranceSizeIsNull_receiveStatus400() throws Exception{
        var addressDTO = createValidAddressDTO();
        addressDTO.setEntrance(null);
        assertThat(
                updateAddress(addressDTO).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenNameIsNull_receiveStatus400() throws Exception{
        var userDTO = registerUser();
        userDTO.setName(null);
        assertThat(
                putRequest(userDTO, userDTO.getJwt()).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateUser_whenNameIsEmpty_receiveStatus400() throws Exception{
        var userDTO = registerUser();
        userDTO.setName("");
        assertThat(
                putRequest(userDTO, userDTO.getJwt()).getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
