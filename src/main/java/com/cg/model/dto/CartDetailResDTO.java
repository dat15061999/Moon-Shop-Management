package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CartDetailResDTO {
    private Long idCartDetail;
    private Long idProduct;
    private String productName;
    private BigDecimal productPrice;
    private String url;
    private int quantity;
    private BigDecimal totalAmount;
    public CartDetailResDTO(Long idCartDetail,Long idProduct, String productName, BigDecimal productPrice, String url, int quantity, BigDecimal totalAmount) {
        this.idCartDetail= idCartDetail;
        this.idProduct = idProduct;
        this.productName = productName;
        this.productPrice = productPrice;
        this.url = url;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }
}
