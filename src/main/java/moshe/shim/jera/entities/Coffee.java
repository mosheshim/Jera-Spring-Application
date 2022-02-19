package moshe.shim.jera.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.payload.CoffeeDTO;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Coffee extends Product{
    public static final int LIGHT_ROAST = 1;
    public static final int MEDIUM_ROAST = 2;
    public static final int DARK_ROAST = 3;


    @NotEmpty
    private String countryOfOrigin;

    @Range(min = 1, max = 3)
    private Integer roastingLevel;

    @NotEmpty
    private String tasteProfile;

    @Range(min = 0, max = 5)
    private Integer bitterness;

    @Range(min = 0, max = 5)
    private Integer sweetness;

    @Range(min = 0, max = 5)
    private Integer acidity;

    @Range(min = 0, max = 5)
    private Integer body;

}
