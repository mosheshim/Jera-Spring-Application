package moshe.shim.jera.services;

import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.payload.CoffeeDTO;

import java.util.List;
import java.util.Set;

public interface CoffeeService {
    CoffeeDTO addCoffee(CoffeeDTO coffee);
    Set<CoffeeDTO> getAllCoffee();
}
