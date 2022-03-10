package moshe.shim.jera.repositories;

import moshe.shim.jera.entities.Tea;
import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.entities.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;


public interface TeaRepository extends JpaRepository<Tea, Long> {

    List<Tea> findAllByTeaProductSeries(TeaProductSeries teaProductSeries);

}
