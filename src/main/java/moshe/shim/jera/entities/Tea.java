package moshe.shim.jera.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.models.ProductModel;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tea extends ProductModel {

    @ManyToOne
    @JoinColumn(name = "product_series_id", nullable = false)
    private TeaProductSeries teaProductSeries;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "weights", joinColumns = @JoinColumn(name = "tea_id"))
    @JoinColumn(name = "tea_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Weight> weights = List.of();

}
