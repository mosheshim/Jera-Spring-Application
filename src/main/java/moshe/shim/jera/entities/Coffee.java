package moshe.shim.jera.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.models.ProductModel;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Coffee extends ProductModel {
    public static final int LIGHT_ROAST = 1;
    public static final int MEDIUM_ROAST = 2;
    public static final int DARK_ROAST = 3;


    @Column(name = "country_of_origin",nullable = false)
    private String countryOfOrigin;

    @Range(min = 1, max = 3)
    @Column(name = "roasting_level", nullable = false)
    private Integer roastingLevel;

    @Column(name = "taste_profile", nullable = false)
    private String tasteProfile;

    @NotNull
    @Range(min = 0, max = 5)
    private Integer bitterness;

    @NotNull
    @Range(min = 0, max = 5)
    private Integer sweetness;

    @NotNull
    @Range(min = 0, max = 5)
    private Integer acidity;

    @NotNull
    @Range(min = 0, max = 5)
    private Integer body;

}
