package moshe.shim.jera.tea;

import moshe.shim.jera.TestUtils;
import moshe.shim.jera.entities.Tea;
import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.entities.Weight;
import moshe.shim.jera.payload.TeaDTO;
import moshe.shim.jera.payload.WeightDTO;
import moshe.shim.jera.repositories.TeaProductSeriesRepository;
import moshe.shim.jera.repositories.TeaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static moshe.shim.jera.TestUtils.createValidPSEntity;
import static moshe.shim.jera.controllers.TeaController.API_1_TEA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class TeaTestsUtils extends TestUtils<TeaDTO> {

    @Autowired
    protected TeaRepository teaRepository;
    @Autowired
    protected TeaProductSeriesRepository psRepository;

    protected TeaProductSeries psEntity;


    public TeaTestsUtils() {
        super(TeaDTO.class, API_1_TEA);
    }

    @BeforeEach
    protected void init(){
        var ps = createValidPSEntity();
        entityManager.persistAndFlush(ps);
        psEntity = ps;
    }

    @Override
    protected MockHttpServletResponse postRequest(TeaDTO dto) throws Exception {
        return postRequest(dto, "/product-series/"+psEntity.getId());
    }


    protected MockHttpServletResponse putRequest(WeightDTO dto, long id) throws Exception{
        return mockMvc
                .perform(put(String.format("%s/%d/weight", path, id))
                        .content(asString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
    }

    protected Tea uploadTeaToDB(){
        Tea validTeaEntity = createValidTeaEntity();
        entityManager.persistAndFlush(validTeaEntity);
        return validTeaEntity;
    }

    protected Tea createValidTeaEntity(){
        Tea teaEntity = createTeaEntity();
        teaEntity.setTeaProductSeries(psEntity);
        return teaEntity;
    }

    protected static List<Weight> createValidWeightList() {
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

    protected WeightDTO toWeightDTO(Weight weight){
        return new WeightDTO(weight.getWeight(), weight.getPrice(), weight.isInStock());
    }
}
