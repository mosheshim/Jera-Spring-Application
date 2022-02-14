package moshe.shim.jera.payload;

import lombok.Builder;
import lombok.Data;
import moshe.shim.jera.entities.TeaProductSeries;

@Data
@Builder
public class TeaProductSeriesDTO {

    private long id;

    private String name;

    private String description;

    private String prices;

    private Boolean isTeaBag;

    public TeaProductSeries fromDTO(){
        return TeaProductSeries.builder()
                .id(id)
                .name(name)
                .description(description)
                .prices(prices)
                .isTeaBag(isTeaBag)
                .build();
    }
}
