package moshe.shim.jera.payload;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AddressDTO {

    @NotEmpty
    @Size(max = 30)
    private String city;

    @NotEmpty
    @Size(max = 30)
    private String street;

    @NotEmpty
    @Size(max = 10)
    private String houseNumber;

    @NotEmpty
    @Size(max = 20)
    private String zip;

    @NotEmpty
    @Size(max = 10)
    private String floor;

    @NotEmpty
    @Size(max = 10)
    private String apartment;

    @NotEmpty
    @Size(max = 10)
    private String entrance;

}
