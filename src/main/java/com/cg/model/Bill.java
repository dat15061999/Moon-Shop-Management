package com.cg.model;


import com.cg.model.dto.BillResDTO;
import com.cg.model.enums.EPayment;
import com.cg.model.enums.EStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime create_at;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer user;

    private String userName;

    @Enumerated(value = EnumType.STRING)
    private EPayment ePayment;

    private Boolean deleted;

    @OneToMany(mappedBy = "bill")
    private List<BillDetail> billDetailList;

    private BigDecimal total;

    public BillResDTO toBillResDTO () {
        return new BillResDTO()
                .setDate(create_at.toLocalDate())
                .setTotal(total)
                .setId(id)
                ;
    }
}

