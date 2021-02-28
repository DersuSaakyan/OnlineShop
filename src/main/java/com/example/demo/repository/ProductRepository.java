package com.example.demo.repository;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAllByCategoryId(int id);
    List<Product> findAllByCategoryIdAndUser(int id,User user);

    @Transactional
    @Modifying
    @Query("delete from Product p where p.id=?1")
    void deleteProductById(int i);
    List<Product> findAllByUser(User user);

    Product findOneById(int id);

    List<Product> findAllByCategoryName(String str);

    @Procedure(procedureName = "addLike")
    void addLike(int usId,int prId);

    @Transactional
    @Modifying
    @Query("update Product p set p.count=?1 where p.id=?2")
    void updateCart(int count,int id);

    @Transactional
    @Modifying
    @Query("update Product p set p.active=?1 where p.id=?2")
    void updateActive(int active,int id);

}
