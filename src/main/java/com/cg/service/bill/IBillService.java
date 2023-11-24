package com.cg.service.bill;

import com.cg.model.Bill;
import com.cg.model.BillDetail;
import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IBillService extends IGeneralService<Bill,Long> {
    void saveBillDetailFromDetail(Bill bill,CartDetail cartDetail);

    Bill saveBillFromCart(Cart cart);

    List<BillDetail> getBillDetailByBill_Id(Long idBill);

    List<Bill> getAllByUser_Id(Long idCustomer);
}
