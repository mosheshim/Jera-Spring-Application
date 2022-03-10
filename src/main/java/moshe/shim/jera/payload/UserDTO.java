package moshe.shim.jera.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private long id;

    private String email;

    @JsonIgnore
    private String password;

    @NotEmpty
    private String name;

    private String phone;

    private String jwt;

    @Valid
    private AddressDTO address;

    private List<CartItemDTO> cart;

}
