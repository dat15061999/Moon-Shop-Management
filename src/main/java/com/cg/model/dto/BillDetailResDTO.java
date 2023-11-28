package com.cg.model.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class BillDetailResDTO {
    private String productName;
    private String image;
    private BigDecimal productPrice;
    private int quantity;
    private BigDecimal totalAmount;

}
