package com.phonestore.phone_store.dao;

import com.phonestore.phone_store.entity.Cart;
import com.phonestore.phone_store.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDAO extends CrudRepository<Cart, Integer> {
    public List<Cart> findByUser(User user);
}
