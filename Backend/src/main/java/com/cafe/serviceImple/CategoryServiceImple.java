package com.cafe.serviceImple;


import com.cafe.Entity.Category;
import com.cafe.constants.CafeConstant;

import com.cafe.dao.CategoryRepo;
import com.cafe.jwt.CustomerUsersDetailsService;
import com.cafe.jwt.JwtFillter;
import com.cafe.service.CategoryService;
import com.cafe.utils.CafeUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImple implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    JwtFillter jwtFillter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap)
    {
        try {
            if(jwtFillter.isAdmin())
            {
                if(validateCategoryMap(requestMap,false))
                {
                    categoryRepo.save(getCategoryFromMap(requestMap, false));
                    return CafeUtils.getResponseEntity("Category Added Successfully",HttpStatus.OK);
                }
            }
            else
            {
                return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId)
    {
        if(requestMap.containsKey("name")) {
            if(requestMap.containsKey("id") && validateId) {
                return true;
            }else if(!validateId) {
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String,String> requestMap, Boolean isAdd) {
        Category category = new Category();
        if(isAdd)
        {
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue)
    {
        try {
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true"))
            {
                //log.info("Inside if");
                return new ResponseEntity<List<Category>>(categoryRepo.getAllCategory(),HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryRepo.findAll(),HttpStatus.OK);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap)
    {
        try {
            if(jwtFillter.isAdmin()) {
                if(validateCategoryMap(requestMap,true))
                {
                    Optional optional= categoryRepo.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty()) {
                        categoryRepo.save(getCategoryFromMap(requestMap, true));
                        return CafeUtils.getResponseEntity("Category Updated Successfully",HttpStatus.OK);
                    }else {
                        return CafeUtils.getResponseEntity("Category id does not exist",HttpStatus.OK);
                    }
                }
                return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }else {
                return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
