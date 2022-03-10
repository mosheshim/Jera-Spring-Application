package moshe.shim.jera.services.impl;

import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.exceptions.ResourceNotFoundException;
import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.repositories.CoffeeRepository;
import moshe.shim.jera.services.CoffeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static moshe.shim.jera.controllers.CoffeeController.API_1_COFFEE;

@Service
public class CoffeeServiceImpl implements CoffeeService {

    private final CoffeeRepository coffeeRepository;
    private final TypeMap<Coffee, CoffeeDTO> toDTO;
    private final TypeMap<CoffeeDTO, Coffee> toCoffee;

    public CoffeeServiceImpl(CoffeeRepository coffeeRepository, ModelMapper modelMapper) {
        this.coffeeRepository = coffeeRepository;
        this.toDTO = modelMapper.createTypeMap(Coffee.class, CoffeeDTO.class);
        this.toCoffee = modelMapper.createTypeMap(CoffeeDTO.class, Coffee.class);
    }

    @Override
    public CoffeeDTO addCoffee(CoffeeDTO dto) {
        var coffee = toCoffee.map(dto);
        coffee.setUploadDate(new Date());
        return toDTO.map(coffeeRepository.save(coffee));
    }

    @Override
    public CoffeeDTO getCoffeeById(long id) {
        return findCoffeeById(id);
    }

    @Override
    public String updateCoffeeById(long id, CoffeeDTO dto) {
        CoffeeDTO coffeeById = findCoffeeById(id);

        Coffee coffee = toCoffee.map(dto);
        coffee.setId(id);
        coffee.setUploadDate(coffeeById.getUploadDate());
        coffeeRepository.save(coffee);
        return "Updated successfully";

    }

    @Override
    public String deleteById(long id) {
        try {
            coffeeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Coffee", "id", id, API_1_COFFEE);
        }
        return "Deleted Successfully";
    }

    @Override
    public List<CoffeeDTO> getAllCoffee() {
        return coffeeRepository.findAll().stream().map(toDTO::map)
                .collect(Collectors.toList());
    }

    private CoffeeDTO findCoffeeById(long id) {
        return toDTO.map(coffeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Coffee", "Id", id, API_1_COFFEE)));
    }
}
