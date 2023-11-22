package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.ProductResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

//    List<Product> findProductByPosterId(String poster_id);
    List<Product> findAllByDeletedFalse();
//    @Modifying
//    @Query(value = "UPDATE Product set poster = null where id=:id")
//    void  updatePoster(Long id);
    @Query(value = "SELECT p FROM Product p" +
            " WHERE (p.productPrice BETWEEN :min AND :max) AND p.deleted = false "  +
            "AND  ( p.productName LIKE %:search%  " +
            "OR p.description LIKE %:search%  )" )
    Page<Product> searchAllByService(@Param("search") String search, Pageable pageable , @Param("min") BigDecimal min, @Param("max") BigDecimal max);

}
