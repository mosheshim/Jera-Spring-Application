package moshe.shim.jera.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@AllArgsConstructor
public class ResourceNotFound extends RuntimeException {
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;
    private final String path;
}