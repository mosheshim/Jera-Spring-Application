package moshe.shim.jera.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.entities.Coffee;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoffeeDTO extends ProductDTO{

    private String countryOfOrigin;

    private String tasteProfile;

    private Integer roastingLevel;

    private Integer bitterness;

    private Integer sweetness;

    private Integer acidity;

    private Integer body;

    public Coffee fromDTO(){
        var coffee = new Coffee();
        coffee.setId(id);
        coffee.setUploadDate(uploadDate);
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
