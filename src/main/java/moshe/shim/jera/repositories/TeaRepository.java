package moshe.shim.jera.repositories;

import moshe.shim.jera.entities.Tea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeaRepository extends JpaRepository<Tea, Long> {
}
