package com.cg.service.product;

import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Image;
import com.cg.model.Product;
import com.cg.repository.ImageRepository;
import com.cg.repository.ProductRepository;
import com.cg.service.product.request.ProductEditRequest;
import com.cg.service.product.request.ProductSaveRequest;
import com.cg.service.product.response.ProductEditResponse;
import com.cg.service.product.response.ProductListResponse;
import com.cg.until.AppMessage;
import com.cg.until.AppUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.cg.service.request.SelectOptionRequest;
import com.cg.service.response.SelectOptionResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductSer {
    private final ProductRepository productRepository;
    private final ImageRepository fileRepository;

    public ResponseEntity<?> createProduct(ProductSaveRequest request){
        var product = AppUtil.mapper.map(request, Product.class);
        product.setProductName(request.getName());
        product.setProductPrice(new BigDecimal(request.getPrice()));
        product.setDeleted(false);
        productRepository.save(product);
        var images = fileRepository.findAllById(request.getImages().stream().map(SelectOptionRequest::getId).collect(Collectors.toList()));
        for (var image: images) {
            image.setProduct(product);
        }
         fileRepository.saveAll(images);
        return ResponseEntity.ok(".");
    }
    public Page<ProductListResponse> showListProduct(String search, Pageable pageable, BigDecimal min, BigDecimal max){

        return productRepository.searchAllByService(search,pageable,min,max)
                .map(product -> ProductListResponse.builder()
                        .id(product.getId())
                        .name(product.getProductName())
                        .description(product.getDescription())
                        .price(product.getProductPrice())
                        .images(String.valueOf(product.getImageList().stream().map(Image::getUrl).toList()))
                        .deleted(product.getDeleted())
                        .build());
    }
    @Transactional
    public void deleteById(Long id) {
        Product product = findById(id);
        fileRepository.deleteAllByProductId(id);
        product.setDeleted(true);
        productRepository.save(product);
    }
    public Product findById(Long id) {
        //de tai su dung
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMessage.ID_NOT_FOUND, "Product", id)));
    }
    public ProductEditResponse findByIdProduct(Long id) {
        var product = findById(id);
        var result = AppUtil.mapper.map(product, ProductEditResponse.class);
        result.setPrice(product.getProductPrice());
        result.setName(product.getProductName());
        List<String> images = product.getImageList()
                .stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());

        result.setImages(images);
        List<String> idImages = product.getImageList().stream().map(Image::getId).toList();
        result.setIdImages(idImages);
        return result;
    }
    @Transactional
    public ResponseEntity<?>  update(ProductEditRequest request, Long id) throws Exception {
        var productDB = findById(id);
        productDB.getImageList().forEach(e -> {
            e.setProduct(null);
            fileRepository.save(e);
        });
        var images = fileRepository.findAllById(request.getImages().stream().map(SelectOptionRequest::getId).collect(Collectors.toList()));
        for (var image: images) {
            image.setProduct(productDB);
        }
        productDB.setProductName(request.getName());
        productDB.setDescription(request.getDescription());
        productDB.setProductPrice(new BigDecimal(request.getPrice()));


        fileRepository.saveAll(images);
        productRepository.save(productDB);
        return ResponseEntity.ok(".");

    }
    public List<SelectOptionResponse> findAll() {
        return productRepository.findAllByDeletedFalse()
                .stream().map(product -> new SelectOptionResponse(product.getId()
                        .toString(), product.getProductName())).collect(Collectors.toList());
    }
}
