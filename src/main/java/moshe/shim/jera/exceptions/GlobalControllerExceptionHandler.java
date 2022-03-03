package moshe.shim.jera.exceptions;

import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> handleResourceNotFoundException(
            ResourceNotFoundException exc) {
        val details = String.format(
                "%s Not Found, Field: %s, Value: %s",
                exc.getResourceName(),
                exc.getFieldName(),
                exc.getFieldValue());
        val error = ErrorDetailsDTO.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("Resource Not Found")
                .details(details)
                .path(exc.getPath())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDetailsDTO> handleValidationException(
            ValidationException exc) {
        val details = String.format(
                "%s , Field: %s, Value: %s",
                exc.getDetails(),
                exc.getFieldName(),
                exc.getFieldValue());
        val error = ErrorDetailsDTO.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("Illegal Value")
                .details(details)
                .path(exc.getPath())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        val map = new HashMap<String, Object>();
        for (FieldError fieldError : fieldErrors) {
            if (fieldError.getDefaultMessage() != null)
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        val error = ErrorDetailsDTO.builder()
                .status(status.value())
                .message("Validation Error")
                .details(map)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
