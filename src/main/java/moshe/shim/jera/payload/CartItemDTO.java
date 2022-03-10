package moshe.shim.jera.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private long id;

    private String extra;

    private int amount;

    private ProductDTO product;
}
