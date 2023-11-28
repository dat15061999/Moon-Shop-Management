package com.cg.model;
import com.cg.model.dto.BillDetailResDTO;
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

    private String productName;

    private BigDecimal productPrice;

    private BigDecimal totalAmount;

    public BillDetail(Long id) {
        this.id = id;
    }

    public BillDetail(Bill bill, Product product) {
        this.bill = bill;
        this.product = product;
    }
    public BillDetailResDTO toBillDetailResDTO(){
        return new BillDetailResDTO()
                .setImage(product.getPoster().getUrl())
                .setProductPrice(productPrice)
                .setProductName(productName)
                .setQuantity(quantity)
                .setTotalAmount(totalAmount)
                ;
    }
}
