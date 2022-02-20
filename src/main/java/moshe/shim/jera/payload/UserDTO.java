package moshe.shim.jera.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Setter;

@Data
public class UserDTO {
    private long id;

    private String email;

    @JsonIgnore
    private String password;

    private String name;

    private String jwt;

    private AddressDTO address;
}
