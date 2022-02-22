package moshe.shim.jera;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class TestUtils<T> {

    protected final Class<T> type;

    protected final String path;

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
        return mockMvc.perform(
                        post(path)
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
                .perform(put(String.format("%s/%s", path, id))
                        .content(asString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    protected MockHttpServletResponse deleteRequest(long id) throws Exception {
        return mockMvc
                .perform(delete(String.format("%s/%s", path, id))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    abstract protected T createValidDTO();
 

}
