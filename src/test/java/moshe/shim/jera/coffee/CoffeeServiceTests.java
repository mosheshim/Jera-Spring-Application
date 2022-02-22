package moshe.shim.jera.coffee;

import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.exceptions.ResourceNotFoundException;
import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.repositories.CoffeeRepository;
import moshe.shim.jera.services.CoffeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CoffeeServiceTests {

    @Autowired
    CoffeeRepository coffeeRepository;
    @Autowired
    CoffeeService coffeeService;

    @BeforeEach
    private void cleanup(){
        coffeeRepository.deleteAll();
    }

    protected CoffeeDTO createValidDTO() {
        return CoffeeDTO.builder()
                .price(50)
                .name("coffee name")
                .imageUrl("coffee image url")
                .inStock(true)
                .description("coffee description")
                .countryOfOrigin("coffee country of origin")
                .roastingLevel(2)
                .bitterness(4)
                .sweetness(3)
                .acidity(2)
                .body(5)
                .tasteProfile("coffee taste profile")
                .build();
    }

    @Test
    public void saveEntity_whenDTOIsValid_receiveDTOBack(){
        var savedDTO = coffeeService.addCoffee(createValidDTO());
        assertThat(savedDTO).isNotNull();
    }

    @Test
    public void saveEntity_whenDTOIsNotValid_throwMethodArgumentNotValidException(){
        assertThrows(TransactionSystemException.class, () -> coffeeService.addCoffee(new CoffeeDTO()));
    }

    @Test
    public void updateEntity_whenIdIsFoundInDB_updateDTOInDB(){
        var dto = createValidDTO();
        var savedDto = coffeeService.addCoffee(dto);

        savedDto.setName("updated dto");
        coffeeService.updateCoffeeById(savedDto.getId(), savedDto);

        Coffee byId = coffeeRepository.findById(savedDto.getId()).orElse(null);
        assertThat(byId).isNotNull();
        assertThat(byId.getName()).isEqualTo("updated dto");
    }

    @Test
    public void updateEntity_whenUpdateIsSuccessful_receiveString(){
        var dto = createValidDTO();
        var savedDto = coffeeService.addCoffee(dto);
        savedDto.setName("updated dto");
        assertThat(coffeeService.updateCoffeeById(savedDto.getId(), savedDto))
                .isNotNull().isNotEmpty();
    }

    @Test
    public void updateEntity_whenEntityIdIsNotFoundInDB_throwResourceNotFoundException(){
        assertThrows(
                ResourceNotFoundException.class,
                () -> coffeeService.updateCoffeeById(1, createValidDTO()));
    }

    @Test
    public void getDTO_whenIdIsFoundInDB_receiveDTO(){
        var savedDTO = coffeeService.addCoffee(createValidDTO());
        assertThat(coffeeService.getCoffeeById(savedDTO.getId())).isNotNull();
    }

    @Test
    public void getDTO_whenIdIsNotFoundInDB_throwResourceNotFoundException(){
        assertThrows(ResourceNotFoundException.class,
                () -> coffeeService.getCoffeeById(100));
    }

    @Test
    public void deleteEntity_whenIdIsFoundInDB_entityIsDeletedFromDB(){
        var savedDTO = coffeeService.addCoffee(createValidDTO());
        coffeeService.deleteById(savedDTO.getId());
        assertThat(coffeeRepository.findById(savedDTO.getId()).orElse(null)).isNull();
    }

    @Test
    public void deleteEntity_whenIdIsNotFoundInDB_throwResourceNotFoundException(){
        assertThrows(ResourceNotFoundException.class,
                () -> coffeeService.deleteById(100));
    }

    @Test
    public void getAllFromDB_whenThereAreTwoEntities_receiveASetWithTwoDTOs(){
        var dto1 = coffeeService.addCoffee(createValidDTO());

        var beforeSavedDTO = createValidDTO();
        beforeSavedDTO.setName("different name");

        var dto2 = coffeeService.addCoffee(beforeSavedDTO);
        Set<CoffeeDTO> allCoffee = coffeeService.getAllCoffee();

        assertThat(allCoffee.size()).isEqualTo(2);
        assertThat(allCoffee.containsAll(Set.of(dto1, dto2))).isTrue();
    }

    @Test
    public void getAllFromDB_whenThereAreNoEntities_receiveAnEmptySet(){
        Set<CoffeeDTO> allCoffee = coffeeService.getAllCoffee();
        assertThat(allCoffee.size()).isEqualTo(0);
    }


}
