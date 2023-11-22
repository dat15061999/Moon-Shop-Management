package com.cg.model;
import jakarta.persistence.*;
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
@Entity
@Table(name = "bill_details")
@Accessors(chain = true)
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn (name = "bill_id")
    private Bill bill;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    private BigDecimal amount;
    private int feesShipping;
    private String product_name;
    private BigDecimal product_price;
    private String product_size;
    private String product_color;

    private BigDecimal totalAmount;



}
