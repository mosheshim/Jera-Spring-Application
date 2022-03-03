package moshe.shim.jera.repositories;

import moshe.shim.jera.entities.TeaProductSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface TeaProductSeriesRepository extends JpaRepository<TeaProductSeries, Long> {

}
