package com.cg.model.dto;

import com.cg.model.BillDetail;
import com.cg.model.Customer;
import com.cg.model.enums.EPayment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class BillResDTO {

    private Long id;

    private LocalDate date;

    private BigDecimal total;
}
