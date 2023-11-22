package com.cg.repository;

import com.cg.model.Image;
import com.cg.model.dto.ImageResDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,String> {
    @Query("SELECT new com.cg.model.dto.ImageResDTO(i.id, i.url) FROM Image i WHERE i.product.id = :productId")
    List<ImageResDTO> findImagesByProductId(@Param("productId") Long productId);
    void deleteAllByProductId(Long id);
    @Modifying
    @Query(value = "UPDATE Image set product.id = null WHERE product.id = :id")
    void deleteFilesByProductId(@Param("id") Long id);

    List<Image> findAllByProductId(Long id);

    @Query(value = "SELECT * FROM files WHERE id in :myId", nativeQuery = true)
    List<Image> findCuaTao(List<String> myId);
}
