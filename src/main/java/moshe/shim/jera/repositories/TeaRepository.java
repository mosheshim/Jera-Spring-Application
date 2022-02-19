package moshe.shim.jera.repositories;

import moshe.shim.jera.entities.Tea;
import moshe.shim.jera.entities.TeaProductSeries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TeaRepository extends JpaRepository<Tea, Long> {
    Set<Tea> findAllByNameAndTeaProductSeries(String name, TeaProductSeries teaProductSeries);
}
