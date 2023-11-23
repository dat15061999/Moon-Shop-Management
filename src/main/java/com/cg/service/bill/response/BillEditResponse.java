package com.cg.service.bill.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class BillEditResponse {
    private Long id;

    private LocalDateTime timeBook;
    private LocalDateTime appointmentTime;
    private List<Long> productsId;
    private List<BigDecimal> productsPrice;
    private List<String> productsName;

    private Long users;
    private String userName;
    private BigDecimal price;
    private String ePayment;

}
