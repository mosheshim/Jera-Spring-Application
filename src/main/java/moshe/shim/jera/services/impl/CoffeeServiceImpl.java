package moshe.shim.jera.services.impl;

import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.repositories.CoffeeRepository;
import moshe.shim.jera.services.CoffeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CoffeeServiceImpl implements CoffeeService {

    private final CoffeeRepository coffeeRepository;
    private final TypeMap<Coffee, CoffeeDTO> toDTO;
    private final TypeMap<CoffeeDTO ,Coffee> toCoffee;

    public CoffeeServiceImpl(CoffeeRepository coffeeRepository, ModelMapper modelMapper) {
        this.coffeeRepository = coffeeRepository;
        this.toDTO = modelMapper.createTypeMap(Coffee.class, CoffeeDTO.class);
        this.toCoffee = modelMapper.createTypeMap(CoffeeDTO.class ,Coffee.class);
    }

    @Override
    public CoffeeDTO addCoffee(CoffeeDTO dto){
        return toDTO.map(coffeeRepository.save(toCoffee.map(dto)));
    }

    @Override
    public Set<CoffeeDTO> getAllCoffee(){
        return coffeeRepository.findAll().stream().map(toDTO::map)
                .collect(Collectors.toSet());
    }
}
