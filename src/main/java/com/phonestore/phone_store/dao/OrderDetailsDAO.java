package com.phonestore.phone_store.dao;

import com.phonestore.phone_store.entity.OrderDetails;
import com.phonestore.phone_store.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsDAO extends CrudRepository<OrderDetails, Integer> {
    public List<OrderDetails> findByUser(User user);
    public List<OrderDetails> findByOrderStatus(String status);
}
