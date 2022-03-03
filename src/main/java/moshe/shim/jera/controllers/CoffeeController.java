package moshe.shim.jera.controllers;

import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.services.CoffeeService;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
        return new ResponseEntity<>(service.addCoffee(coffee), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CoffeeDTO>> getAllCoffee(){
        return ResponseEntity.ok(service.getAllCoffee());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoffeeDTO> getCoffeeById(@PathVariable long id){
        return ResponseEntity.ok(service.getCoffeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCoffeeById(@PathVariable long id, @Valid @RequestBody CoffeeDTO dto){
        return new ResponseEntity<>(service.updateCoffeeById(id, dto), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoffeeById(@PathVariable long id){
        return new ResponseEntity<>(service.deleteById(id), HttpStatus.NO_CONTENT);
    }


}

