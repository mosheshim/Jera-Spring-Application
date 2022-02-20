package moshe.shim.jera.controllers;

import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.services.CoffeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static moshe.shim.jera.controllers.CoffeeController.API_1_COFFEE;

@RequestMapping(API_1_COFFEE)
@RestController
public class CoffeeController {
    public final static String API_1_COFFEE = "/api/1/coffee";

    private final CoffeeService service;


    public CoffeeController(CoffeeService service) {
        this.service = service;

    }

    @PostMapping
    public ResponseEntity<CoffeeDTO> addCoffee(@Valid @RequestBody CoffeeDTO coffee){
        return ResponseEntity.ok(service.addCoffee(coffee));
    }

    @GetMapping()
    public ResponseEntity<Set<CoffeeDTO>> getAllCoffee(){
        return ResponseEntity.ok(service.getAllCoffee());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoffeeDTO> getCoffeeById(@PathVariable long id){
        return ResponseEntity.ok(service.getCoffeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoffeeDTO> updateCoffeeById(@PathVariable long id, @Valid @RequestBody CoffeeDTO dto){
        return ResponseEntity.ok(service.updateCoffeeById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoffeeById(@PathVariable long id){
        return ResponseEntity.ok(service.deleteById(id));
    }


}

