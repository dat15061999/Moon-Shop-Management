package com.cg.model;

import com.cg.model.dto.CartDetailResDTO;
import com.cg.model.dto.ProductResDTO;
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
@Table(name = "cart_details")
@Accessors(chain = true)
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn (name = "product_id")
    private Product product;

    private int quantity;

    private BigDecimal amount;

    private String product_size;

    private String product_color;

}
