package com.phonestore.phone_store.service;

import com.phonestore.phone_store.configuration.JwtRequestFilter;
import com.phonestore.phone_store.dao.CartDAO;
import com.phonestore.phone_store.dao.OrderDetailsDAO;
import com.phonestore.phone_store.dao.ProductDAO;
import com.phonestore.phone_store.dao.UserDAO;
import com.phonestore.phone_store.entity.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {
    private static final String ORDER_PLACED = "Placed";
    @Autowired
    private OrderDetailsDAO orderDetailsDAO;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CartDAO cartDAO;

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckOut) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();


        for(OrderProductQuantity o: productQuantityList) {
            Optional<Product> productOptional = Optional.of(productDAO.findById(o.getProductId()).get());

            String currentUser = JwtRequestFilter.CURRENT_USER;
            Optional<User> userOptional = Optional.of(userDAO.findById(currentUser).get());

            if (productOptional.isPresent()  && userOptional.isPresent()) {
                Product product = productOptional.get();
                User user = userOptional.get();

                OrderDetails orderDetails = new OrderDetails(
                        orderInput.getFullName(),
                        orderInput.getFullAddress(),
                        orderInput.getContactNumber(),
                        orderInput.getAlternateContactNumber(),
                        ORDER_PLACED,
                        product.getProductDiscountedPrice() * o.getQuantity(),
                        product,
                        user
                );
                orderDetailsDAO.save(orderDetails);

                //Empty the card
                if(!isSingleProductCheckOut) {
                   List<Cart> carts =  cartDAO.findByUser(user);
                   carts.forEach(x -> cartDAO.deleteById(x.getCartId()));
                }
            } else {
                // Xử lý khi không tìm thấy Product hoặc User
                System.out.println("Ko tim thấy user hoặc product");
            }
        }
    }
    public List<OrderDetails> getOrderDetails() {
        String username = JwtRequestFilter.CURRENT_USER;
        User user = userDAO.findById(username).get();
        return orderDetailsDAO.findByUser(user);
    }
    public List<OrderDetails> getAllOrderDetails(String status) {
        List<OrderDetails> orderDetails = new ArrayList<>();

        if(status.equals("All")) {
            orderDetailsDAO.findAll().forEach(
                    x -> orderDetails.add(x)
            );
        }else {
            orderDetailsDAO.findByOrderStatus(status).forEach(x -> orderDetails.add(x));
        }
        return orderDetails;
    }
    public void markOrderAsDelivered(Integer orderId) {
        OrderDetails  orderDetails = orderDetailsDAO.findById(orderId).get();

        if(orderDetails !=  null) {
            orderDetails.setOrderStatus("Delivered");
            orderDetailsDAO.save(orderDetails);
        }
    }



}
