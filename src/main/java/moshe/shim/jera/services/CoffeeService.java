package moshe.shim.jera.services;

import moshe.shim.jera.entities.Coffee;

import java.util.List;

public interface CoffeeService {
    public Coffee addCoffee(Coffee coffee);
    public List<Coffee> getAllCoffee();
}
