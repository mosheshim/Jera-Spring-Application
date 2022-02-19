package moshe.shim.jera.payload;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeaProductSeriesDTO {

    private long id;

    private String name;

    private String description;

    private String prices;

    private Boolean isTeaBag;

    private Set<TeaDTO> teaList;

}
