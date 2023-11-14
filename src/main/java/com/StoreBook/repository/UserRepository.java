package com.StoreBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
