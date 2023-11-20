package com.cg.service.bill;

import com.cg.model.Bill;
import com.cg.model.BillDetail;
import com.cg.service.IGeneralService;

public interface IBillService extends IGeneralService<Bill,Long> {
    void saveBillDetail(BillDetail billDetail);
}
