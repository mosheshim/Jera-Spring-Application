package moshe.shim.jera.controllers;

import moshe.shim.jera.services.CoffeeService;
import moshe.shim.jera.services.impl.CoffeeServiceImpl;
import moshe.shim.jera.entities.Coffee;
import moshe.shim.jera.exceptions.APIErrorMessageDTO;
import moshe.shim.jera.shared.GenericResponse;
import org.springframework.http.HttpStatus;
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
    public GenericResponse addCoffee(@Valid @RequestBody Coffee coffee){
        service.addCoffee(coffee);
        return GenericResponse.builder().message("coffee saved").build();
    }

    @GetMapping()
    public List<Coffee> getAllCoffee(){
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

