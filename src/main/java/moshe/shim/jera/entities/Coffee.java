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
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Coffee extends Product{
    public static final int LIGHT_ROAST = 1;
    public static final int MEDIUM_ROAST = 2;
    public static final int DARK_ROAST = 3;

    @NotEmpty
    @NotNull
    private String countryOfOrigin;

    @Range(min = 0, max = 3)
    @NotNull
    private Integer roastingLevel;

    @NotNull
    private String tasteProfile;

    @Range(min = 0, max = 5)
    @NotNull
    private Integer bitterness;

    @Range(min = 0, max = 5)
    @NotNull
    private Integer sweetness;

    @Range(min = 0, max = 5)
    @NotNull
    private Integer acidity;

    @Range(min = 0, max = 5)
    @NotNull
    private Integer body;


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public CoffeeDTO toDTO(){
        var coffee = new CoffeeDTO();
        coffee.setId(id);
        coffee.setName(name);
        coffee.setImageUrl(imageUrl);
        coffee.setInStock(inStock);
        coffee.setDescription(description);
        coffee.setCountryOfOrigin(countryOfOrigin);
        coffee.setTasteProfile(tasteProfile);
        coffee.setRoastingLevel(roastingLevel);
        coffee.setBitterness(bitterness);
        coffee.setSweetness(sweetness);
        coffee.setAcidity(acidity);
        coffee.setBody(body);
        return coffee;
    }

}
