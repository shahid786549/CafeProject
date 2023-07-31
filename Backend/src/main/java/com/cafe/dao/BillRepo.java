package com.cafe.dao;

import com.cafe.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepo extends JpaRepository<Bill, Integer> {
    List<Bill> getAllBills();

    List<Bill> getBillByUserName(@Param("username") String username);
}
