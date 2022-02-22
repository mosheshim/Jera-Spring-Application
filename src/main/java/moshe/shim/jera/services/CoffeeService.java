package moshe.shim.jera.services;

import moshe.shim.jera.payload.CoffeeDTO;

import java.util.Set;

public interface CoffeeService {
    CoffeeDTO addCoffee(CoffeeDTO coffee);
    CoffeeDTO getCoffeeById(long id);
    String updateCoffeeById(long id, CoffeeDTO dto);
    String deleteById(long id);
    Set<CoffeeDTO> getAllCoffee();

}
