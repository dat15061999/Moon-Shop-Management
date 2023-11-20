package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.dto.CartDetailResDTO;
import com.cg.repository.CartDetailRepository;
import com.cg.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService implements ICartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long idCustomer) {
        return Optional.empty();
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void saveCartDetail(CartDetail cartDetail) {
        cartDetailRepository.save(cartDetail);
    }

    @Override
    public List<CartDetailResDTO> getAllByCustomer_Id(Long idCustomer) {
        return cartRepository.getAllByCustomer_Id(idCustomer);
    }

    @Override
    public Optional<Cart> findByIdCustomer(Long idCustomer) {
        return cartRepository.findCartByCustomer(idCustomer);
    }

    @Override
    public long existsByIdProduct(Long idProduct) {
        return cartRepository.existsByIdProduct(idProduct);
    }

    @Override
    public void saveCartDetailIsExitWithProduct(CartDetail cartDetail) {
        Optional<CartDetail> cartDetailOld = cartDetailRepository.findCartDetailByProductId(cartDetail.getProduct().getId());

        CartDetail newCartDetail = cartDetailOld.get();
        newCartDetail.setAmount(newCartDetail.getAmount().add(cartDetail.getAmount()));
        newCartDetail.setProduct_size(cartDetail.getProduct_size());
        newCartDetail.setProduct_color(cartDetail.getProduct_color());
        newCartDetail.setQuantity(newCartDetail.getQuantity()+cartDetail.getQuantity());

        cartDetailRepository.save(newCartDetail);
    }

    @Override
    public void deleteCartDetail(Long idCartDetail) {
        cartDetailRepository.deleteById(idCartDetail);
    }

    @Override
    public long getCountDetail(Long idCustomer) {
        return cartDetailRepository.getCount(idCustomer);
    }
}
