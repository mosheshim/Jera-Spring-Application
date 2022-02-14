package moshe.shim.jera.repositories;

import moshe.shim.jera.entities.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, String> {


}
