package com.cg.repository;

import com.cg.model.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {
    @Query(value = "SELECT b FROM Bill b WHERE b.deleted = false AND " +
            "(b.userName LIKE %:search% OR " +

            "EXISTS (SELECT 1 FROM BillDetail bp WHERE bp.bill = b AND bp.product.productName LIKE %:search%))")
    Page<Bill> searchAllByBill(@Param("search") String search, Pageable pageable);
}
