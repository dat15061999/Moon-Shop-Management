package com.cg.repository;

import com.cg.model.Image;
import com.cg.model.dto.ImageResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query("SELECT new com.cg.model.dto.ImageResDTO(i.id, i.url) FROM Image i WHERE i.product.id = :productId")
    List<ImageResDTO> findImagesByProductId(@Param("productId") Long productId);
}
