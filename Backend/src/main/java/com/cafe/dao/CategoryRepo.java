package com.cafe.dao;

import com.cafe.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Component
public interface CategoryRepo extends JpaRepository<Category,Integer> {

    List<Category> getAllCategory();
}
