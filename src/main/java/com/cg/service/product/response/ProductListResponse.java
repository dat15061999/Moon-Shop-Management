package com.cg.service.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class ProductListResponse {
    private Long id;
    private String name;
    private String description;
    private List<Long> productsId;
    private Boolean deleted;

    private BigDecimal price;
    private String images;
}
