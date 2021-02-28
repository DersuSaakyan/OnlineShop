package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findOneByEmail(String s);

    boolean existsUserByEmail(String s);

    boolean existsUserByPhone(String s);

    User findOneByPhone(String s);

    @Transactional
    @Modifying
    @Query("update User u set u.code=?1 where u.email=?2")
    void updateUserByEmail(int code, String s);

    User findOneById(int id);
    @Transactional
    @Modifying
    @Query("update User u set u.active=?1 where u.id=?2")
    void updateUserByActive(int active, int id);


    @Query("select u from User u where u.active>0")
    List<User> findAllByActive();

}
