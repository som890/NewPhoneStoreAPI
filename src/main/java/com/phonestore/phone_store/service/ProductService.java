package com.phonestore.phone_store.service;

import com.phonestore.phone_store.configuration.JwtRequestFilter;
import com.phonestore.phone_store.dao.CartDAO;
import com.phonestore.phone_store.dao.ProductDAO;
import com.phonestore.phone_store.dao.UserDAO;
import com.phonestore.phone_store.entity.Cart;
import com.phonestore.phone_store.entity.Product;
import com.phonestore.phone_store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CartDAO cartDAO;
    public Product addNewProduct(Product product) {
        return productDAO.save(product);
    }
    public List<Product> getAllProduct(int pageNumber, String searchKey) {
        PageRequest pageable = PageRequest.of(pageNumber, 12);
        if(searchKey.isEmpty()) {
            return (List<Product>) productDAO.findAll(pageable);
        }else {
            return productDAO.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey, searchKey, pageable);
        }
    }
    public void deleteProductDetails(Integer productId) {
        productDAO.deleteById(productId);
    }
    public Product getProductDetailsById(Integer productId) {
        return productDAO.findById(productId).get();
    }
    public List<Product> getProductDetails(boolean isSingleProductCheckOut, Integer productId) {
        if(isSingleProductCheckOut && productId != 0) {
            // Buy a single product
            List<Product> productList = new ArrayList<>();
            Product product = productDAO.findById(productId).get();
            productList.add(product);
            return productList;
        }else {
            //Buy entire product
            String username = JwtRequestFilter.CURRENT_USER;
            User user = userDAO.findById(username).get();
            List<Cart> carts = cartDAO.findByUser(user);

            return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());
        }
    }
}
