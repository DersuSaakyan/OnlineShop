package com.example.demo.repository;

import com.example.demo.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Cart findOneByUserIdAndProductId(int usId,int prId);
    @Transactional
    @Modifying
    @Query("update Cart c set c.quantity=?1 where c.id=?2")
    void updateCart(int quantity,int id);
    List<Cart> findAllByUserId(int id);
    @Transactional
    @Modifying
    @Query("delete from Cart c where c.productId=?1")
    void deleteCartByProductId(int id);
}
