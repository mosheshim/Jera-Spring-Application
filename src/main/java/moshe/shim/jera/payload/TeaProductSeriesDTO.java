package moshe.shim.jera.payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeaProductSeriesDTO {

    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @Builder.Default
    private String imageUrl = "default url";

    @NotEmpty
    private String prices;

    private boolean isTeaBag;

    private Set<TeaDTO> teaList;

}
