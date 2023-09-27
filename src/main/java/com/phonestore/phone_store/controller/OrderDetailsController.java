package com.phonestore.phone_store.controller;

import com.phonestore.phone_store.entity.OrderDetails;
import com.phonestore.phone_store.entity.OrderInput;
import com.phonestore.phone_store.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OrderDetailsController {
    @Autowired
    private OrderDetailsService orderDetailsService;
    @PreAuthorize("hasRole('User')")
    @PostMapping("/placeOrder/{isSingleProductCheckOut}")
    public void placeOrder(@PathVariable(name = "isSingleProductCheckOut")boolean isSingleProductCheckOut,
                           @RequestBody OrderInput orderInput) {
        orderDetailsService.placeOrder(orderInput, isSingleProductCheckOut);
    }
    @PreAuthorize("hasRole('User')")
    @GetMapping("/getOrderDetails")
    public List<OrderDetails> getOrderDetails() {
        return orderDetailsService.getOrderDetails();
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/getAllOrderDetails/{status}")
    public List<OrderDetails> getAllOrderDetails(@PathVariable(name = "status") String status) {
        return orderDetailsService.getAllOrderDetails(status);
    }
    @GetMapping("/markOrderAsDelevered/{orderId}")
    public void markOrderAsDeleivered(@PathVariable(name = "orderId") Integer orderId) {
        orderDetailsService.markOrderAsDelivered(orderId);
    }
}
