package com.cafe.serviceImple;

import com.cafe.Entity.Product;
import com.cafe.dao.ProductRepo;
import com.cafe.service.ProductService;
import com.cafe.Entity.Category;
import com.cafe.constants.CafeConstant;


import com.cafe.jwt.JwtFillter;

import com.cafe.utils.CafeUtils;

import com.cafe.wraper.ProductWrapper;
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
public class ProductServiceImple implements ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    JwtFillter jwtFillter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {

        try {
            if(jwtFillter.isAdmin())
            {
                if(validateProductMap(requestMap,false))
                {
                    productRepo.save(getProductFromMap(requestMap,false));
                    return CafeUtils.getResponseEntity("Product add Successfully",HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }else
            {
                return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }
    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {

        if(requestMap.containsKey("name")) {
            if(requestMap.containsKey("id") && validateId) {
                return true;
            }else if(!validateId) {
                return true;
            }
        }
        return false;
    }
    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd)
    {

        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();

        if (isAdd)
        {
            product.setId(Integer.parseInt(requestMap.get("id")));
        }
        else
        {
            product.setStatus("true");
        }
        product.setName (requestMap.get("name"));
        product.setDescription (requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;
    }
    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {
            return new ResponseEntity<>(productRepo.getAllProduct(),HttpStatus.OK);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if(jwtFillter.isAdmin())
            {
                if(validateProductMap(requestMap,true))
                {
                    Optional<Product> optional =	productRepo.findById(Integer.parseInt(requestMap.get("id")));
                    if(!optional.isEmpty())
                    {
                        Product product = getProductFromMap(requestMap, true);
                        product.setStatus(optional.get().getStatus());
                        productRepo.save(product);
                        return CafeUtils.getResponseEntity("Product Updated Successfully",HttpStatus.OK);
                    }else {
                        return CafeUtils.getResponseEntity("Product id does not exist.",HttpStatus.OK);
                    }
                }
                else
                {
                    return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
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

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            if (jwtFillter.isAdmin()){
            Optional optional= productRepo.findById(id);
            if (!optional.isEmpty()){
                productRepo.deleteById(id);
                return CafeUtils.getResponseEntity("Product Deleted SuccessFully",HttpStatus.OK);

            }else {
                return CafeUtils.getResponseEntity("Product id does not exist.",HttpStatus.OK);
            }
            }else{
                return  CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            if (jwtFillter.isAdmin()){
             Optional optional=  productRepo.findById(Integer.parseInt(requestMap.get("id")));
             if (!optional.isEmpty()){
                 productRepo.updateProductStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                 return CafeUtils.getResponseEntity("Product Status Updated SuccessFully.", HttpStatus.OK);

             }else {
                return  CafeUtils.getResponseEntity("Product id does not exist.",HttpStatus.OK);
             }
            }else {
                return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try{
        return  new ResponseEntity<>(productRepo.getProductByCategory(id),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try{
        return new ResponseEntity<>(productRepo.getProductById(id),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
