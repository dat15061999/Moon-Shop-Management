package com.cg.api;


import com.cg.service.product.ProductSer;
import com.cg.service.product.ProductService;
import com.cg.service.product.request.ProductEditRequest;
import com.cg.service.product.request.ProductSaveRequest;
import com.cg.service.product.response.ProductEditResponse;
import com.cg.service.product.response.ProductListResponse;
import com.cg.until.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/productsAdmin")
@AllArgsConstructor
public class ProductRestController {
    private final ProductSer productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductSaveRequest productSaveRequest , BindingResult bindingResult){
        new ProductSaveRequest().validate(productSaveRequest, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return AppUtil.mapErrorToResponse(bindingResult);
        }
        return new ResponseEntity<>(productService.createProduct(productSaveRequest), HttpStatus.CREATED);

    }
    @GetMapping
    public ResponseEntity<Page<ProductListResponse>> list(@PageableDefault(size = 5) Pageable pageable,
                                                          @RequestParam(defaultValue = "") String search,
                                                          @RequestParam(defaultValue = "1") BigDecimal min,
                                                          @RequestParam(defaultValue = "500000000000000000") BigDecimal max
    ) {
        return new ResponseEntity<>(productService.showListProduct(search,pageable, min,max), HttpStatus.OK);
    }
    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long Id) {
        productService.deleteById(Id);
        return ResponseEntity.ok("Product deleted successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductEditResponse> find(@PathVariable Long id){
        return  new ResponseEntity<>(productService.findByIdProduct(id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ProductEditRequest request , BindingResult bindingResult, @PathVariable Long id) throws Exception {
        new ProductEditRequest().validate(request, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return AppUtil.mapErrorToResponse(bindingResult);
        }
        productService.update(request,id);
        return ResponseEntity.noContent().build();
    }
}
