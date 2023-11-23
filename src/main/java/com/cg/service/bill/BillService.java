package com.cg.service.bill;

import com.cg.model.Bill;
import com.cg.model.BillDetail;
import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.enums.EPayment;
import com.cg.repository.BillDetailRepository;
import com.cg.repository.BillRepository;
import com.cg.repository.CartDetailRepository;
import com.cg.service.cart.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillService implements IBillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public List<Bill> findAll() {
        return null;
    }

    @Override
    public Optional<Bill> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Bill bill) {
        billRepository.save(bill);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void saveBillDetailFromDetail(Bill bill,CartDetail cartDetail) {

        BillDetail billDetails = new BillDetail();

        billDetails.setBill(bill);

        billDetails.setProduct(cartDetail.getProduct());

        billDetails.setProductName(cartDetail.getProductName());

        billDetails.setProductPrice(cartDetail.getProductPrice());

        billDetails.setQuantity(cartDetail.getQuantity());

        billDetails.setTotalAmount(cartDetail.getTotalAmount());

        billDetailRepository.save(billDetails);

    }

    @Override
    public Bill saveBillFromCart(Cart cart) {
        BigDecimal total = cartDetailRepository.getTotalAmountByCustomer_Id(cart.getId());

        Bill bill = new Bill();

        bill.setCreate_at(cart.getCreate_at());

        bill.setUser(cart.getCustomer());

        bill.setUserName(cart.getCustomer().getName());

        bill.setTotal(total);

        bill.setEPayment(EPayment.NONE);

        bill.setDeleted(false);

        billRepository.save(bill);

        return bill;

    }
}
