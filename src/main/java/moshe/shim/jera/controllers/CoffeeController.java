package moshe.shim.jera.controllers;

import moshe.shim.jera.payload.CoffeeDTO;
import moshe.shim.jera.services.CoffeeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RequestMapping("/api/1/coffee")
@RestController
public class CoffeeController {

    private final CoffeeService service;


    public CoffeeController(CoffeeService service, ModelMapper modelMapper) {
        this.service = service;

    }

    @PostMapping
    public ResponseEntity<CoffeeDTO> addCoffee(@Valid @RequestBody CoffeeDTO coffee){
        return ResponseEntity.ok(service.addCoffee(coffee));
    }

    @GetMapping()
    public Set<CoffeeDTO> getAllCoffee(){
        return service.getAllCoffee();
    }

//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    ValidationError handleMethodArgumentNotValidException(
//            MethodArgumentNotValidException e, HttpServletRequest request){
//        var hashMap = new HashMap<String, String>();
//        var fieldsErrors = e.getBindingResult().getFieldErrors();
//        for (FieldError fieldsError : fieldsErrors) {
//            hashMap.put(fieldsError.getField(), fieldsError.getDefaultMessage());
//        }
//        return new ValidationError(
//                "Validation error",
//                HttpStatus.BAD_REQUEST.value(),
//                request.getServletPath(),
//                hashMap
//        );
//    }

}

