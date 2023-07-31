package com.cafe.serviceImple;

import com.cafe.dao.BillRepo;
import com.cafe.dao.CategoryRepo;
import com.cafe.dao.ProductRepo;
import com.cafe.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class DashboardServiceImple implements DashboardService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    BillRepo billRepo;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        // TODO Auto-generated method stub
        Map<String, Object> map = new HashMap<>();
        map.put("category", categoryRepo.count());
        map.put("product",productRepo.count());
        map.put("bill",billRepo.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
