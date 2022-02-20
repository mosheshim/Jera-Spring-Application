package moshe.shim.jera.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.models.ProductModel;

import javax.persistence.*;


@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tea extends ProductModel {

    private Integer weight;

    @ManyToOne(fetch = FetchType.EAGER) //only if we try to access
    @JoinColumn(name = "teaProductSeries_id", nullable = false)
    private TeaProductSeries teaProductSeries;


}
