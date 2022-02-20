package moshe.shim.jera.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserExistsException extends RuntimeException{
    private String details;
}
