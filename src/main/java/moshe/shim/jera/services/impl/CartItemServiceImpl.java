package moshe.shim.jera.services.impl;

import moshe.shim.jera.payload.CartItemDTO;
import moshe.shim.jera.services.CartItemService;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Override
    public CartItemDTO addCartItem(String bearerToken, CartItemDTO dto) {
        return null;
    }
}
