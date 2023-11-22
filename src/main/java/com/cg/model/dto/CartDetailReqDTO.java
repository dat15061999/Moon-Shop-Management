package com.cg.model.dto;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Customer;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class CartDetailReqDTO {
    private Long idProduct;
    private Long idCustomer;
    private String productName;
    private BigDecimal productPrice;
    private int amount;
    private BigDecimal totalAmount;

    public Cart toCart(){
        return new Cart()
                .setCustomer(new Customer(idCustomer))
                .setCreate_at(LocalDateTime.now());
    }
    public CartDetail toCartDetail(){
        return new CartDetail()
                .setTotalAmount(totalAmount)
                .setProduct(new Product(idProduct))
                .setProduct_name(productName)
                .setProduct_price(productPrice)
                .setQuantity(amount)
                ;
    }

}
