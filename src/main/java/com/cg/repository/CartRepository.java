package com.cg.repository;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.dto.CartDetailResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT new com.cg.model.dto.CartDetailResDTO(\n" +
            " cd.id, \n" +
            " cd.product.id,\n" +
            " cd.productName,\n" +
            " cd.productPrice,\n" +
            " i.url,\n" +
            " cd.quantity,\n" +
            " cd.totalAmount )\n" +
            " FROM \n" +
            " Cart c \n" +
            " join \n" +
            " CartDetail cd \n" +
            " on \n" +
            "     c.id = cd.cart.id \n" +
            "join \n" +
            "      Product p\n" +
            "on \n" +
            "     cd.product.id = p.id\n" +
            "join \n" +
            "     Image i \n" +
            "on \n" +
            "     p.poster.id = i.id\n" +
            "where c.customer.id  = :idCustomer  group by cd.id")
     List<CartDetailResDTO> getAllByCustomer_Id(@Param("idCustomer") Long idCustomer);

    @Query("SELECT " +
            " c " +
            "FROM " +
            "Cart c " +
            "WHERE " +
            "c.customer.id = :idCustomer")
    Optional<Cart> findCartByCustomer(@Param("idCustomer") Long idCustomer);


    @Query("select " +
            "   count(cd) " +
            "from " +
            "   CartDetail cd " +
            "join " +
            "   Cart c " +
            "on " +
            "   c.id=cd.cart.id " +
            "where " +
            "   c.customer.id = :idCustomer  " +
            "and " +
            "   cd.product.id = :idProduct")
    long existsByIdProduct(@Param("idProduct") Long idProduct,@Param("idCustomer") Long idCustomer);



}
