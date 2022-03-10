package moshe.shim.jera.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.ObjectInputValidation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    @NotNull
    @Email
    private String email;

    @NotEmpty
    private String name;

    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$", message = """
            Password Must Contain at least one Uppercase, Lowercase and Digit
            """)
    private String password;


}
