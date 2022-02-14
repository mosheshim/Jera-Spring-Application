package moshe.shim.jera.services;

import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.payload.CoffeeDTO;

import java.util.List;

public interface CoffeeService {
    public CoffeeDTO addCoffee(CoffeeDTO coffee);
    public List<CoffeeDTO> getAllCoffee();
}
