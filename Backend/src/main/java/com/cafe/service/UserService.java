package com.cafe.service;

import com.cafe.wraper.UserWapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<String>signUp(Map<String,String>requestMap);

    ResponseEntity<String>login(Map<String,String>requestMap);

    ResponseEntity<List<UserWapper>> getAllUser();

    ResponseEntity<String>update(Map<String,String>requestMap);

    ResponseEntity<String>checkToken();

    ResponseEntity<String>changePassword(Map<String,String>requestMap);

    ResponseEntity<String>forgotPassword(Map<String,String>requestMap);

}
