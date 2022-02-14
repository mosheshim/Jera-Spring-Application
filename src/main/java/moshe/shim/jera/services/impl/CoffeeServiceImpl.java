package moshe.shim.jera.services.impl;

import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.repositories.CoffeeRepository;
import moshe.shim.jera.services.CoffeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeServiceImpl implements CoffeeService {
    private final CoffeeRepository repo;

    public CoffeeServiceImpl(CoffeeRepository repo) {
        this.repo = repo;
    }

    @Override
    public Coffee addCoffee(Coffee coffee){
        return repo.save(coffee);
    }

    @Override
    public List<Coffee> getAllCoffee(){
        return repo.findAll();
    }
}
