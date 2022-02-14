package moshe.shim.jera.services.impl;

import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.repositories.CoffeeRepository;
import moshe.shim.jera.services.CoffeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeServiceImpl implements CoffeeService {
    private final CoffeeRepository repo;

    public CoffeeServiceImpl(CoffeeRepository repo) {
        this.repo = repo;
    }

    @Override
    public CoffeeDTO addCoffee(CoffeeDTO dto){
        Coffee coffee = repo.save(dto.fromDTO());
        return coffee.toDTO();
    }

    @Override
    public List<CoffeeDTO> getAllCoffee(){
        return repo.findAll().stream().map(Coffee::toDTO
        ).collect(Collectors.toList());
    }
}
