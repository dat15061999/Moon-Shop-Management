package com.cg.service.product.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductEditResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

    private List<String> images;
    private List<String> idImages;

}
