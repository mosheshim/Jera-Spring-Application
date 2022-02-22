package moshe.shim.jera.entities;

import lombok.*;
import moshe.shim.jera.payload.TeaProductSeriesDTO;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private String prices;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_tea_bag")
    private boolean isTeaBag;

    @OneToMany(mappedBy = "teaProductSeries", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "tea_list")
    private Set<Tea> teaSet;

}
