package moshe.shim.jera.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TeaProductSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    private String prices;

    private Boolean isTeaBags;

    @OneToMany(mappedBy = "teaProductSeries", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tea> teas = new HashSet<>();
}
