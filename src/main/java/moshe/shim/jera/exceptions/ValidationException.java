package moshe.shim.jera.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException{
    private final String details;
    private final String fieldName;
    private final Object fieldValue;
    private final String path;
}
