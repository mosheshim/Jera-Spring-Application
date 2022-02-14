package moshe.shim.jera.entities;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Coffee extends Product{
    public static final int LIGHT_ROAST = 1;
    public static final int MEDIUM_ROAST = 2;
    public static final int DARK_ROAST = 3;

    @NotEmpty
    @NotNull
    private String countryOfOrigin;

    @Range(min = 0, max = 5)
    @NotNull
    private int roastingLevel;

    @NotNull
    private String tasteProfile;

    @Range(min = 0, max = 5)
    @NotNull
    private int bitterness;

    @Range(min = 0, max = 5)
    @NotNull
    private int sweetness;

    @Range(min = 0, max = 5)
    @NotNull
    private int acidity;

    @Range(min = 0, max = 5)
    @NotNull
    private int body;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Coffee(@NotNull int price, @NotNull @NotEmpty String name, @NotNull String imageUrl, @NotNull Boolean inStock, @NotNull String description, String countryOfOrigin, int roastingLevel, String tasteProfile, int bitterness, int sweetness, int acidity, int body) {
        super(price, name, imageUrl, inStock, description);
        this.countryOfOrigin = countryOfOrigin;
        this.roastingLevel = roastingLevel;
        this.tasteProfile = tasteProfile;
        this.bitterness = bitterness;
        this.sweetness = sweetness;
        this.acidity = acidity;
        this.body = body;
    }

}
