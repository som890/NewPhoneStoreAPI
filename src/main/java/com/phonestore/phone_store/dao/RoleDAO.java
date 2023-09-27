package com.phonestore.phone_store.dao;

import com.phonestore.phone_store.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role,String> {
}