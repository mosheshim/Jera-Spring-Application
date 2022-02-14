package moshe.shim.jera.entities;

import lombok.*;
import moshe.shim.jera.payload.TeaProductSeriesDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TeaProductSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    private String prices;

    private Boolean isTeaBag;

    @OneToMany(mappedBy = "teaProductSeries", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tea> teas = new HashSet<>();

    public TeaProductSeriesDTO toDTO(){
        return TeaProductSeriesDTO.builder()
                .id(id)
                .name(name)
                .description(description)
                .prices(prices)
                .isTeaBag(isTeaBag)
                .build();
    }
}
