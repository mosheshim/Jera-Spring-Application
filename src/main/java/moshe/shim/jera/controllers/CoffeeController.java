package moshe.shim.jera.controllers;

import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.services.CoffeeService;
import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.exceptions.APIErrorMessageDTO;
import moshe.shim.jera.shared.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/api/1/coffee")
@RestController
public class CoffeeController {
    private final CoffeeService service;

    public CoffeeController(CoffeeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CoffeeDTO> addCoffee(@Valid @RequestBody CoffeeDTO coffee){
        return ResponseEntity.ok(service.addCoffee(coffee));
    }

    @GetMapping()
    public List<CoffeeDTO> getAllCoffee(){
        return service.getAllCoffee();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    APIErrorMessageDTO handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request){
        var hashMap = new HashMap<String, String>();
        var fieldsErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldsError : fieldsErrors) {
            hashMap.put(fieldsError.getField(), fieldsError.getDefaultMessage());
        }
        return new APIErrorMessageDTO(
                "Validation error",
                HttpStatus.BAD_REQUEST.value(),
                request.getServletPath(),
                hashMap
        );
    }

}

