package moshe.shim.jera.coffee;

import moshe.shim.jera.TestUtils;
import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.exceptions.ResourceNotFoundException;
import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.repositories.CoffeeRepository;
import moshe.shim.jera.services.CoffeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import javax.transaction.Transactional;
import java.util.*;

import static moshe.shim.jera.TestUtils.createValidCoffeeDTO;
import static moshe.shim.jera.TestUtils.createValidCoffeeEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class CoffeeServiceTests{

    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private TestEntityManager entityManager;

    private Coffee uploadCoffeeEntity(){
        return entityManager.persistAndFlush(createValidCoffeeEntity());
    }

    @Test
    public void saveEntity_whenDTOIsValid_receiveDTOBack(){
        var savedDTO = coffeeService.addCoffee(createValidCoffeeDTO());
        assertThat(savedDTO).isNotNull();
    }

    @Test
    public void updateEntity_whenIdIsFoundInDB_updateDTOInDB(){
        var entity = uploadCoffeeEntity();

        var dto = createValidCoffeeDTO();
        dto.setName("updated dto");
        coffeeService.updateCoffeeById(entity.getId(), dto);

        Coffee byId = coffeeRepository.findById(entity.getId()).orElse(null);
        assertThat(byId).isNotNull();
        assertThat(byId.getName()).isEqualTo("updated dto");
    }

    @Test
    public void updateEntity_whenUpdateIsSuccessful_receiveString(){
        var entity = uploadCoffeeEntity();

        var dto = createValidCoffeeDTO();
        dto.setName("updated dto");
        var string = coffeeService.updateCoffeeById(entity.getId(), dto);

        assertThat(string).isEqualTo("Updated successfully");
    }

    @Test
    public void updateEntity_whenEntityIdIsNotFoundInDB_throwResourceNotFoundException(){
        assertThrows(
                ResourceNotFoundException.class,
                () -> coffeeService.updateCoffeeById(100, createValidCoffeeDTO()));
    }

    @Test
    public void getDTO_whenIdIsFoundInDB_receiveDTO(){
        var entity = uploadCoffeeEntity();
        assertThat(coffeeService.getCoffeeById(entity.getId())).isNotNull();
    }

    @Test
    public void getDTO_whenIdIsNotFoundInDB_throwResourceNotFoundException(){
        assertThrows(ResourceNotFoundException.class,
                () -> coffeeService.getCoffeeById(100));
    }

    @Test
    public void deleteEntity_whenIdIsFoundInDB_entityIsDeletedFromDB(){
        var entity = uploadCoffeeEntity();
        coffeeService.deleteById(entity.getId());
        assertThat(coffeeRepository.findById(entity.getId()).orElse(null)).isNull();
    }

    @Test
    public void deleteEntity_whenIdIsNotFoundInDB_throwResourceNotFoundException(){
        assertThrows(ResourceNotFoundException.class,
                () -> coffeeService.deleteById(100));
    }

    @Test
    public void getAllFromDB_whenThereAreTwoEntities_receiveAListWithTwoDTOs(){
        uploadCoffeeEntity();
        uploadCoffeeEntity();

        assertThat(coffeeRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void getAllFromDB_whenThereAreNoEntities_receiveAnEmptyList(){
        List<CoffeeDTO> allCoffee = coffeeService.getAllCoffee();
        assertThat(allCoffee.size()).isEqualTo(0);
    }


}
