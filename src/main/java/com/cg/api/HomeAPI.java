package com.cg.api;


import com.cg.model.Image;
import com.cg.model.Product;
import com.cg.model.dto.ImageResDTO;
import com.cg.model.dto.ProductResDTO;
import com.cg.repository.ImageRepository;
import com.cg.repository.ProductRepository;
import com.cg.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class HomeAPI {
    @Autowired
    private IProductService productService;
    @Autowired
    private ImageRepository imageRepository;
    @GetMapping
    public ResponseEntity<?> showAll(){
        List<Product> products = productService.findAll();
        List<ProductResDTO> productResDTOs = products.stream()
                .map(product -> new ProductResDTO(
                        product.getId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getScore(),
                        product.getProductPrice(),
                        imageRepository.findImagesByProductId(product.getId())))
                .collect(Collectors.toList());

        return new ResponseEntity<>(productResDTOs, HttpStatus.OK);
    }
    @GetMapping("{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId){
        Optional<Product> product = productService.findById(productId);
        ProductResDTO productResDTO = product.orElseThrow().toProductResDTO();
        return new ResponseEntity<>(productResDTO,HttpStatus.OK);
    }

}
