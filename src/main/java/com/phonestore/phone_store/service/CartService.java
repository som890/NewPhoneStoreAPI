package com.phonestore.phone_store.service;

import com.phonestore.phone_store.configuration.JwtRequestFilter;
import com.phonestore.phone_store.dao.CartDAO;
import com.phonestore.phone_store.dao.ProductDAO;
import com.phonestore.phone_store.dao.UserDAO;
import com.phonestore.phone_store.entity.Cart;
import com.phonestore.phone_store.entity.Product;
import com.phonestore.phone_store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDAO userDAO;
    public Cart addToCart(Integer productId) {
        Product product = productDAO.findById(productId).get();
        String username = JwtRequestFilter.CURRENT_USER;

        User user = null;
        if(username != null) {
           user = userDAO.findById(username).get();
        }

        List<Cart> cartList = cartDAO.findByUser(user);
        List<Cart>  filteredCartList = cartList.stream().filter(x -> x.getProduct().getProductId() == productId).collect(Collectors.toList());

        if(filteredCartList.size() > 0) {
            return null;
        }

        if((product != null) && (user != null)) {
            Cart cart  = new Cart(product, user);
            return cartDAO.save(cart);
        }
        return null;
    }
    public List<Cart> getCartDetails() {
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userDAO.findById(username).get();
        return cartDAO.findByUser(user);
    }

    public void deleteCartItem(Integer cardId) {
        cartDAO.deleteById(cardId);
    }
}
