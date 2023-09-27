package com.phonestore.phone_store.dao;

import com.phonestore.phone_store.entity.Cart;
import com.phonestore.phone_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User,String> {

}