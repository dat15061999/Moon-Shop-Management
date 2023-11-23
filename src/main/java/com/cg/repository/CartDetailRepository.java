package com.cg.repository;

import com.cg.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail,Long> {
    Optional<CartDetail> findCartDetailByProductId(Long idProduct);

    @Query("select " +
            "   count(cd) " +
            "from " +
            "   CartDetail cd " +
            "join " +
            "   Cart c " +
            "on " +
            "   c.id = cd.cart.id " +
            "where" +
            "   c.customer.id = :idCustomer")
    long getCount(@Param("idCustomer") Long idCustomer);

    @Query("select " +
            "   cd " +
            "from " +
            "   CartDetail cd " +
            "join " +
            "   Cart c " +
            "on " +
            "   c.id = cd.cart.id " +
            "where" +
            "   c.customer.id = :idCustomer")
    List<CartDetail> findCartDetailByCartCustomer_Id(@Param("idCustomer") Long idCustomer);

    @Query("select \n" +
            "sum(cd.totalAmount) \n" +
            "from \n" +
            "CartDetail cd \n" +
            "join \n" +
            "Cart c \n" +
            "on \n" +
            "c.id = cd.cart.id \n" +
            "where c.id = :idCart")
    BigDecimal getTotalAmountByCustomer_Id(@Param("idCart") Long idCart);
}
