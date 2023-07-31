package com.cafe.rest;

import com.cafe.Entity.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path ="/bill")
public interface BillRest {

    @PostMapping(path="/generatereport")
    public ResponseEntity<String> generateReport(@RequestBody(required = true) Map<String, Object> requestMap);


    @GetMapping(path = "/getBills")
    ResponseEntity<List<Bill>> getBills();

    @PostMapping(path = "/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String, Object>requMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String>deleteBill(@PathVariable Integer id);

}
