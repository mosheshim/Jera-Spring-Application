package moshe.shim.jera.tea;

import moshe.shim.jera.TestsUtils;
import moshe.shim.jera.entities.Tea;
import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.entities.Weight;
import moshe.shim.jera.payload.TeaDTO;
import moshe.shim.jera.payload.WeightDTO;
import moshe.shim.jera.repositories.TeaProductSeriesRepository;
import moshe.shim.jera.repositories.TeaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

public abstract class TeaTestsUtils extends TestsUtils<TeaDTO> {

    @Autowired
    protected TeaRepository teaRepository;
    @Autowired
    protected TeaProductSeriesRepository psRepository;

    protected TeaProductSeries psEntity;

    public TeaTestsUtils() {
        super(TeaDTO.class, "tea");
    }

    @BeforeEach
    protected void init(){
        psEntity = entityManager.persistAndFlush(createValidPSEntity());

    }

    @Override
    protected MockHttpServletResponse postRequest(Object dto) throws Exception {
        return postRequest(dto, defaultEndPoint+ "/product-series/" + psEntity.getId());
    }

    protected MockHttpServletResponse putWeightRequest(WeightDTO dto, long teaId) throws Exception {
        return putRequest(dto,  defaultEndPoint + "/" + teaId + "/weight");
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

//    protected static List<Weight> createValidWeightList() {
//        var weights = new ArrayList<Weight>();
//        for (int i = 0; i < 3; i++) {
//            weights.add(Weight.builder()
//                    .price(10 * i)
//                    .weight(50 * i)
//                    .inStock(i < 1)
//                    .build());
//        }
//        return weights;
//    }

    protected WeightDTO toWeightDTO(Weight weight){
        return new WeightDTO(weight.getWeight(), weight.getPrice(), weight.isInStock());
    }
}
