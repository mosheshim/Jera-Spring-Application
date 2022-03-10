package moshe.shim.jera.services;


import moshe.shim.jera.entities.CartItem;
import moshe.shim.jera.payload.CartItemDTO;

public interface CartItemService {
    CartItemDTO addCartItem(String bearerToken, CartItemDTO dto);

}
