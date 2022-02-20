package moshe.shim.jera.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenException extends RuntimeException{
    private final String details;

    public TokenException(String token ,String details) {
        this.details = String.format("Token: %s is %s", token, details);
    }
}
