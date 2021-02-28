package com.example.demo.repository;

import com.example.demo.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface WishListRepository extends JpaRepository<WishList,Integer> {
    int countByUserIdAndProductId(int usId,int prId);
    @Transactional
    @Modifying
    @Query("delete from WishList w where w.id=?1")
    void deleteWishListById(int i);

    WishList findOneByUserIdAndProductId(int usId,int prId);
}
