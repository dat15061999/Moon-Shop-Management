package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.ProductResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

//    @Query("SELECT new com.cg.model.dto.ProductResDTO(" +
//            "                p.id, " +
//            "                p.productName, " +
//            "                p.description, " +
//            "                p.score, " +
//            "                p.productPrice, " +
//            "                (SELECT new com.cg.model.dto.ImageResDTO(i.id, i.url) " +
//            "                       FROM Image i WHERE i.product.id = p.id)" +
//            "                ) FROM Product p")
//    List<ProductResDTO> findAllProductResDTO();
}
