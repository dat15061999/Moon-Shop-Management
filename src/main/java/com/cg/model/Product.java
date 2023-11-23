package com.cg.model;
import com.cg.model.dto.ProductResDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
@Accessors(chain = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_price")
    private BigDecimal productPrice;
    private float score;
    private String description;
    private String status;
    private Boolean deleted;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> imageList;
    @OneToMany(mappedBy = "product")
    private List<BillDetail> billProducts;
    @ManyToOne
    @NotNull(message = "Not null")
    private Image poster;

    public Product(Long id) {
        this.id = id;
    }
    public ProductResDTO toProductResDTO() {
        return new ProductResDTO()
                .setId(id)
                .setProductPrice(productPrice)
                .setProductName(productName)
                .setScore(score)
                .setDescription(description)
                .setPoster(poster)
                .setImageList(imageList.stream().map(Image::toImageResDTO).collect(Collectors.toList()));
    }
}
