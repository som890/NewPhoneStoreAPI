package com.phonestore.phone_store.controller;

import com.phonestore.phone_store.entity.Cart;
import com.phonestore.phone_store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {
    @Autowired
    private CartService cartService;
    @PreAuthorize("hasRole('User')")
    @GetMapping("/addToCart/{productId}")
    public Cart addToCart(@PathVariable(name = "productId") Integer productId) {
        return cartService.addToCart(productId);
    }
    @PreAuthorize("hasRole('User')")
    @GetMapping("/getCartDetails")
    public List<Cart> getCartDetails() {
        return cartService.getCartDetails();
    }
    @PreAuthorize("hasRole('User')")
    @DeleteMapping("/deleteCartItem/{cardId}")
    public void deleteCartItem(@PathVariable(name="cardId") Integer cardId) {
        cartService.deleteCartItem(cardId);
    }

}
