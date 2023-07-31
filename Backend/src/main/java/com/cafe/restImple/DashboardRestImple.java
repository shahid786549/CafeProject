package com.cafe.restImple;

import com.cafe.rest.DashboardRest;
import com.cafe.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController

public class DashboardRestImple implements DashboardRest {


    @Autowired(required = true)
    DashboardService dashboardService;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {

        return dashboardService.getCount();
    }


}
