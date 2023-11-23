package com.cg.model.dto;
import com.cg.model.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ProductResDTO {

    private Long id;
    private String productName;
    private String description;
    private float score;
    private BigDecimal productPrice;
    private Image poster;
    private List<ImageResDTO> imageList;

    public ProductResDTO(Long id, String productName, String description, float score, BigDecimal productPrice,Image poster,  List<ImageResDTO> imageList) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.score = score;
        this.productPrice = productPrice;
        this.poster = poster;
        this.imageList = imageList;
    }
}
