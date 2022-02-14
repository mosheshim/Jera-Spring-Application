package moshe.shim.jera.entities;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teas")
@Entity
public class Tea extends Product{

    @ManyToOne(fetch = FetchType.EAGER) //only if we try to access
    @JoinColumn(name = "teaProductSeries_id", nullable = false)
    private TeaProductSeries teaProductSeries;

}
