package com.cafe.dao;

import com.cafe.Entity.User;
import com.cafe.wraper.UserWapper;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    User findByEmailId(@Param("email") String email);

   List<UserWapper> getAllUser();

    List<String> getAllAdmin();

   @Transactional
   @Modifying
   Integer updateStatus(@Param("status") String status,@Param("id") Integer id);

   User findByEmail(String email);
}
