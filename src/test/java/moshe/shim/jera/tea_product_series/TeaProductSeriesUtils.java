package moshe.shim.jera.tea_product_series;

import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.repositories.TeaProductSeriesRepository;
import moshe.shim.jera.repositories.TeaRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.transaction.Transactional;

import static moshe.shim.jera.TestsUtils.createTeaEntity;
import static moshe.shim.jera.TestsUtils.createValidPSEntity;

@Transactional
public class TeaProductSeriesUtils {
    protected final TeaRepository teaRepository;
    protected final TeaProductSeriesRepository psRepository;
    protected final TestEntityManager entityManager;

    public TeaProductSeriesUtils(TeaRepository teaRepository, TeaProductSeriesRepository psRepository, TestEntityManager entityManager) {
        this.teaRepository = teaRepository;
        this.psRepository = psRepository;
        this.entityManager = entityManager;
    }

    protected TeaProductSeries uploadPsAndTea() {
        var ps = uploadPSEntity();

        var tea = createTeaEntity();
        tea.setTeaProductSeries(ps);

        entityManager.persistAndFlush(tea);
        return ps;
    }

    protected TeaProductSeries uploadPSEntity() {
        var psEntity = createValidPSEntity();
        entityManager.persistAndFlush(psEntity);
        return psEntity;
    }


}
