package com.cafe.jwt;

import com.cafe.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;
@Component
public class CustomerUsersDetailsService implements UserDetailsService {
     @Autowired
     UserRepo userRepo;

     private com.cafe.Entity.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        userDetail=userRepo.findByEmailId(username);
                if(!Objects.isNull(userDetail))
                    return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());
                else
                    throw new UsernameNotFoundException("User Not Found");
    }
    public com.cafe.Entity.User getUserDetail(){
         com.cafe.Entity.User user=userDetail;
         user.setPassword(null);
        return userDetail;
    }
}
