package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.dto.CartDetailResDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ICartService extends IGeneralService<Cart,Long> {
    void saveCartDetail(CartDetail cartDetail);
    List<CartDetailResDTO> getAllByCustomer_Id(Long idCustomer);

    Optional<Cart> findByIdCustomer(Long idCustomer);

    long existsByIdProduct(Long idProduct,Long idCustomer);

    void saveCartDetailIsExitWithProduct(CartDetail cartDetail);

    void deleteCartDetail(Long idCartDetail);

    long getCountDetail(Long idCustomer);

}
