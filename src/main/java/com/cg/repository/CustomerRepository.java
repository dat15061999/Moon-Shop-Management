package com.cg.repository;

import com.cg.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "select u from Customer u where u.name LIKE :search OR u.email LIKE :search OR u.phone Like :search   ")
    Page<Customer> searchAllUser(@Param("search") String search, Pageable pageable );
    Optional<Customer> findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(String name, String email, String phone);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);
}