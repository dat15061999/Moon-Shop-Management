package com.cg.service.bill;

import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Bill;
import com.cg.model.BillDetail;
import com.cg.model.Customer;
import com.cg.model.enums.EPayment;
import com.cg.repository.BillDetailRepository;
import com.cg.repository.BillRepository;
import com.cg.repository.CustomerRepository;
import com.cg.repository.ProductRepository;
import com.cg.service.bill.request.BillCreateRequest;
import com.cg.service.bill.response.BillEditResponse;
import com.cg.service.bill.response.BillListResponse;
import com.cg.until.AppMessage;
import com.cg.until.AppUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class BillSer {
    private BillRepository billRepository;
    private ProductRepository productRepository;
    private BillDetailRepository billProductRepository;
    private CustomerRepository userRepository;
    public Page<BillListResponse> findAll(String search, Pageable pageable){
        return billRepository.searchAllByBill(search, pageable)
                .map(b -> BillListResponse.builder()
                        .id(b.getId())
                        .userName(b.getUserName())
                        .createAt(String.valueOf(b.getCreate_at()))
                        .ePayment(String.valueOf(b.getEPayment()))
                        .products(b.getBillDetailList().stream()
                                .map(BillDetail::getProductName)
                                .collect(Collectors.joining(", ")))
                        .productAmount(b.getBillDetailList().stream()
                                .map(BillDetail::getTotalAmount).toList().toString())
                        .build());
    }
//    public Bill create(BillCreateRequest billCreateRequest){
//        Bill bill = AppUtil.mapper.map(billCreateRequest, Bill.class);
//        Customer customer= userRepository.findById(Long.parseLong(billCreateRequest.getUser().getId())).get();
//        bill.setUser(customer);
//        bill.setUserName(customer.getName());
//        bill.setDeleted(false);
//        bill.setEPayment(EPayment.NONE);
//        bill.setCreate_at(LocalDateTime.now());
//        Bill finalBill =billRepository.save(bill);
//        List<Long> idProducts = billCreateRequest.getIdProducts().stream().map(Long::valueOf).toList();
//        List<BillDetail> billDetails = billProductRepository.saveAll(productRepository.findAllById(idProducts).stream().map(product -> {
//            var result =  new BillDetail(finalBill,product);
//            result.setProductName(product.getProductName());
//            result.setProductPrice(product.getProductPrice());
//            result.setQuantity(Integer.parseInt(billCreateRequest.getQuantity()));
//            result.setTotalAmount(product.getProductPrice().multiply(new BigDecimal(billCreateRequest.getQuantity())));
//            return result;
//        }).collect(Collectors.toList()));
//        BigDecimal totalPriceBillProduct = BigDecimal.ZERO;
//        for (BillDetail billProduct : billDetails) {
//            totalPriceBillProduct = totalPriceBillProduct.add(billProduct.getTotalAmount());
//        }
//
//        finalBill.setPrice(totalPriceBillProduct);
//        return billRepository.save(finalBill);
//    }
//    public Bill findById(Long id){
//        return billRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
//                (String.format(AppMessage.ID_NOT_FOUND, "Bill", id)));
//    }
//    public BillEditResponse showEdit(Long id){
//        var bill = findById(id);
//        var billEditResponse = AppUtil.mapper.map(bill,BillEditResponse.class);
//        billEditResponse.setUsers(bill.getUser().getId());
//        billEditResponse.setUserName(bill.getUser().getName());
//
//
//        billEditResponse.setProductsId(bill.getBillDetailList()
//                .stream().map(billProduct -> billProduct.getProduct().getId())
//                .collect(Collectors.toList())
//        );
//        billEditResponse.setProductsPrice(bill.getBillDetailList()
//                .stream().map(BillDetail::getProductPrice)
//                .collect(Collectors.toList())
//        );
//        billEditResponse.se(bill.getBillDetailList()
//                .stream().map(BillDetail::getProductPrice)
//                .collect(Collectors.toList())
//        );
//
//        billEditResponse.setProductsName(bill.getBillProducts()
//                .stream().map(BillProduct::getProductName)
//                .collect(Collectors.toList()));
//
//        return billEditResponse;
//    }
//    public void update (BillEditRequest billEditRequest , Long id){
//        var billDB = findById(id);
//        billDB.setCustomerName(billEditRequest.getCustomerName());
//        billDB.setCustomerPhone(billEditRequest.getCustomerPhone());
//        billDB.setCustomerEmail(billEditRequest.getCustomerEmail());
//        billDB.setCustomerQuantity(Long.valueOf(billEditRequest.getCustomerQuantity()));
//        billDB.setTimeBook(LocalDateTime.now());
//        billDB.setAppointmentTime(LocalDateTime.parse(billEditRequest.getAppointmentTime()));
//        if(billEditRequest.getUser() == null || billEditRequest.getUser().getId().equals("")){
//            billDB.setUser(null);
//        } else {
//            User user = userRepository.findById(Long.valueOf(billEditRequest.getUser().getId())).get();
//            if(billEditRequest.getCustomerName() == null || billEditRequest.getCustomerName().equals("")){
//                billDB.setCustomerName(user.getName());
//            }  if( billEditRequest.getCustomerEmail() == null || billEditRequest.getCustomerEmail().equals("")){
//                billDB.setCustomerEmail(user.getEmail());
//            }  if(billEditRequest.getCustomerPhone() == null || billEditRequest.getCustomerPhone().equals("")) {
//                billDB.setCustomerPhone(user.getPhone());
//            }
//            billDB.setUser(user);
//        }
//        var finalBill = billRepository.save(billDB);
//        billProductRepository.deleteAllById(finalBill.getBillProducts()
//                .stream().map(BillProduct::getId)
//                .collect(Collectors.toList())
//        );
//        billComboRepository.deleteAllById(finalBill.getBillCombos()
//                .stream().map(BillCombo::getId)
//                .collect(Collectors.toList())
//        );
//        List<Long> idCombos = billEditRequest.getIdCombos().stream().map(Long::valueOf).toList();
//        List<BillCombo> billCombos = billComboRepository.saveAll(comboRepository.findAllById(idCombos).stream().map(combo -> {
//            var result =  new BillCombo(finalBill,combo);
//            result.setComboName(combo.getName());
//            result.setComboPrice(combo.getPrice());
//            return result;
//        }).collect(Collectors.toList()));
//        BigDecimal totalPriceBillCombo = BigDecimal.ZERO;
//        for (BillCombo billCombo : billCombos) {
//            totalPriceBillCombo = totalPriceBillCombo.add(billCombo.getComboPrice());
//        }
//
//        List<Long> idProducts = billEditRequest.getIdProducts().stream().map(Long::valueOf).toList();
//        List<BillProduct> billProducts = billProductRepository.saveAll(productRepository.findAllById(idProducts).stream().map(product -> {
//            var result =  new BillProduct(finalBill,product);
//            result.setProductName(product.getName());
//            result.setProductPrice(product.getPrice());
//            return result;
//        }).collect(Collectors.toList()));
//        BigDecimal totalPriceBillProduct = BigDecimal.ZERO;
//        for (BillProduct billProduct : billProducts) {
//            totalPriceBillProduct = totalPriceBillProduct.add(billProduct.getProductPrice());
//        }
//        finalBill.setPrice(totalPriceBillProduct.add(totalPriceBillCombo));
//        billRepository.save(finalBill);
//    }
//    public void paidById(Long id){
//        Bill bill = findById(id);
//        bill.setEPayment(EPayment.PAID);
//        billRepository.save(bill);
//    }
//    public void unpaidById(Long id){
//        Bill bill = findById(id);
//        bill.setEPayment(EPayment.UNPAID);
//        billRepository.save(bill);
//    }
//    public void lockById(Long id){
//        Bill bill = findById(id);
//        bill.setDeleted(true);
//        billRepository.save(bill);
//    }
}
