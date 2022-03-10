package moshe.shim.jera.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;


@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tea extends Product {

    @ManyToOne
    @JoinColumn(name = "product_series_id", nullable = false)
    private TeaProductSeries teaProductSeries;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "weights", joinColumns = @JoinColumn(name = "tea_id", nullable = false, updatable = false))
    @JoinColumn(name = "tea_id")
    @Cascade(CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Weight> weights;

}
