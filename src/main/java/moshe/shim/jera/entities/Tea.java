package moshe.shim.jera.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.payload.TeaDTO;

import javax.persistence.*;


@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tea extends Product{

    private Integer weight;

    @ManyToOne(fetch = FetchType.EAGER) //only if we try to access
//    @JoinColumn(name = "teaProductSeries_id", nullable = false)
    private TeaProductSeries teaProductSeries;


}
