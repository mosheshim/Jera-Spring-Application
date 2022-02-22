package moshe.shim.jera.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.models.ProductModel;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;


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

    @NotNull
    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Range(min = 1, max = 3)
    @NotNull
    @Column(name = "roasting_level")
    private Integer roastingLevel;

    @NotNull
    @Column(name = "taste_profile")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Coffee coffee = (Coffee) o;

        if (!countryOfOrigin.equals(coffee.countryOfOrigin)) return false;
        if (!roastingLevel.equals(coffee.roastingLevel)) return false;
        if (!tasteProfile.equals(coffee.tasteProfile)) return false;
        if (!bitterness.equals(coffee.bitterness)) return false;
        if (!sweetness.equals(coffee.sweetness)) return false;
        if (!acidity.equals(coffee.acidity)) return false;
        return body.equals(coffee.body);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + countryOfOrigin.hashCode();
        result = 31 * result + roastingLevel.hashCode();
        result = 31 * result + tasteProfile.hashCode();
        result = 31 * result + bitterness.hashCode();
        result = 31 * result + sweetness.hashCode();
        result = 31 * result + acidity.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }
}
