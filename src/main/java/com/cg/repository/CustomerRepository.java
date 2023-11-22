package com.cg.repository;

import com.cg.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "SELECT u from Customer as u where u.name LIKE %:search OR u.email LIKE %:search ")
    Page<Customer> searchAllByUserName(@Param("search") String search, Pageable pageable);
    Optional<Customer> findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(String name, String email, String phone);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);


    Optional<Customer> getCustomerByName(String name);

    List<Customer> findAllByDeletedFalse();


}