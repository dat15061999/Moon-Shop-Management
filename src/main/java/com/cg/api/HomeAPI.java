package com.cg.api;

import com.cg.exception.AppUtils;
import com.cg.exception.GlobalExceptionHandler;
import com.cg.model.*;
import com.cg.model.dto.*;
import com.cg.repository.ImageRepository;
import com.cg.repository.ProductRepository;
import com.cg.service.bill.IBillService;
import com.cg.service.cart.ICartService;
import com.cg.service.product.IProductService;
import com.cg.service.product.request.ProductSaveRequest;
import com.cg.service.userService.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class HomeAPI {
    @Autowired
    private IProductService productService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IBillService billService;
    @Autowired
    private UserService userService;
    @Autowired
    private AppUtils appUtils;

    private ProductRepository productRepository;
    @GetMapping
    public ResponseEntity<Page<?>> showAll(@PageableDefault(size = 6) Pageable pageable,
                                           @RequestParam(defaultValue = "") String search,
                                           @RequestParam(defaultValue = "1") BigDecimal min,
                                           @RequestParam(defaultValue = "500000000000000000") BigDecimal max){
        Page<ProductResDTO> products = productRepository.searchAllByService(search,pageable,min,max)
                .map(product -> new ProductResDTO(

                        product.getId(),

                        product.getProductName(),

                        product.getDescription(),

                        product.getScore(),

                        product.getProductPrice(),

                        product.getPoster(),

                        imageRepository.findImagesByProductId(product.getId())));


        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {

        Optional<Product> product = productService.findById(productId);

        ProductResDTO productResDTO = product.orElseThrow().toProductResDTO();

        return new ResponseEntity<>(productResDTO, HttpStatus.OK);
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addProToCart(@RequestBody CartDetailReqDTO cartDetailReqDTO) {
        Long idCustomer = userService.getCurrentCustomer().get().getId();

        Optional<Cart> cart = cartService.findByIdCustomer(idCustomer);

        CartDetail cartDetail = cartDetailReqDTO.toCartDetail();

        if (cart.isPresent()) {

            cartDetail.setCart(cart.get());

            if (cartService.existsByIdProduct(cartDetail.getProduct().getId(), idCustomer) > 0) {

                cartService.saveCartDetailIsExitWithProduct(cartDetail);
            } else {

                cartService.saveCartDetail(cartDetail);
            }
        } else {
            Cart newCart = cartDetailReqDTO.toCart();

            newCart.setCustomer(new Customer(idCustomer));

            cartService.save(newCart);

            cartDetail.setCart(newCart);

            cartService.saveCartDetail(cartDetail);
        }
        return new ResponseEntity<>(cartDetailReqDTO, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getCarts() {
        Long idCustomer = userService.getCurrentCustomer().get().getId();

        List<CartDetailResDTO> cartDetails = cartService.getAllByCustomer_Id(idCustomer);

        return new ResponseEntity<>(cartDetails, HttpStatus.OK);
    }

    @GetMapping("delete/{idCartDetail}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable Long idCartDetail) {

        cartService.deleteCartDetail(idCartDetail);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/cartDetail")
    public ResponseEntity<?> getCountDetail() {
        Long idCustomer = userService.getCurrentCustomer().get().getId();

        Long countCartDetailByCustomer = cartService.getCountDetail(idCustomer);

        return new ResponseEntity<>(countCartDetailByCustomer, HttpStatus.OK);
    }

    @PatchMapping("/cartDetail/{idCartDetail}")
    public ResponseEntity<?> changeTotalProductDetail(@PathVariable Long idCartDetail,@Valid @RequestBody CartDetailChangeAmountReqDTO cartDetailChangeAmountReqDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<ErrorDTO> errors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setField(error.getField());
                errorDTO.setMessage(error.getDefaultMessage());
                errors.add(errorDTO);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        Optional<CartDetail> cartDetail = cartService.findByIdCartDetail(idCartDetail);

        CartDetail newCartDetail = cartDetail.get();

        newCartDetail.setQuantity(cartDetailChangeAmountReqDTO.getQuantity());

        BigDecimal newTotal = BigDecimal.valueOf(cartDetailChangeAmountReqDTO.getQuantity());

        newCartDetail.setTotalAmount(newCartDetail.getProductPrice().multiply(newTotal));

        cartService.saveCartDetail(newCartDetail);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/customer")
    public ResponseEntity<?> getCustomerByID() {
        Long idCustomer = userService.getCurrentCustomer().get().getId();

        Customer customer = userService.findById(idCustomer);

        CustomerResDTO customerResDTO = customer.toCustomerResDTO();

        return new ResponseEntity<>(customerResDTO, HttpStatus.OK);
    }

    @PatchMapping("/customer")
    public ResponseEntity<?> updateCustomerByID(@Valid @RequestBody CustomerReqDTO customerReqDTO, BindingResult bindingResult) {
        customerReqDTO.validate(customerReqDTO, bindingResult);

        Long idCustomer = userService.getCurrentCustomer().get().getId();

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Customer customer = userService.findById(idCustomer);

        customer.setName(customerReqDTO.getName());

        customer.setDob(customerReqDTO.getDob());

        customer.setEmail(customerReqDTO.getEmail());

        customer.setPhone(customerReqDTO.getPhone());

        userService.save(customer);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/bill")
    public ResponseEntity<?> createBill() {
        Long idCustomer = userService.getCurrentCustomer().get().getId();

        Optional<Cart> cart = cartService.findByIdCustomer(idCustomer);

        if (cart.isEmpty()) {
            return new ResponseEntity<>(403, HttpStatus.NOT_FOUND);
        }

        List<CartDetail> cartDetails = cartService.findCartDetailByCartCustomer_Id(idCustomer);

        Bill bill = billService.saveBillFromCart(cart.get());

        for (int i = 0; cartDetails.size() - i > 0; i++) {

            billService.saveBillDetailFromDetail(bill, cartDetails.get(i));

            cartService.deleteCartDetail(cartDetails.get(i).getId());
        }

        cartService.deleteById(cart.get().getId());

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/bill")
    public ResponseEntity<?> getAllBill() {
        Long idCustomer = userService.getCurrentCustomer().get().getId();

        List<Bill> bills = billService.getAllByUser_Id(idCustomer);

        List<BillResDTO> billResDTOS = bills.stream().map(Bill::toBillResDTO).toList();

        return new ResponseEntity<>(billResDTOS, HttpStatus.OK);
    }
    @GetMapping("/bill/{idBill}")
    public ResponseEntity<?> getAllBillDetailById(@PathVariable Long idBill) {

        List<BillDetail> bills = billService.getBillDetailByBill_Id(idBill);

        List<BillDetailResDTO> billDetailResDTOS = bills.stream().map(BillDetail::toBillDetailResDTO).toList();

        return new ResponseEntity<>(billDetailResDTOS, HttpStatus.OK);
    }

}
