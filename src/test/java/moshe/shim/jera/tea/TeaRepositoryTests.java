package moshe.shim.jera.tea;

import moshe.shim.jera.entities.Tea;
import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.entities.Weight;
import moshe.shim.jera.repositories.TeaProductSeriesRepository;
import moshe.shim.jera.repositories.TeaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.util.Pair;

import javax.transaction.Transactional;
import java.util.List;

import static moshe.shim.jera.TestUtils.createTeaEntity;
import static moshe.shim.jera.TestUtils.createValidPSEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest()
@Transactional
@AutoConfigureTestEntityManager
public class TeaRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TeaRepository teaRepository;

    private Tea uploadTeaToDB(TeaProductSeries ps){
        Tea teaEntity = createTeaEntity();
        teaEntity.setTeaProductSeries(ps);
        entityManager.persistAndFlush(teaEntity);

        return teaEntity;
    }

    @Test
    public void findAllByPs_whenTeaWithPsExists_fetchALl() {
        var ps = entityManager.persistAndFlush(createValidPSEntity());
        uploadTeaToDB(ps);
        uploadTeaToDB(ps);
        assertThat(teaRepository.findAllByTeaProductSeries(ps).size()).isEqualTo(2);
    }


}
