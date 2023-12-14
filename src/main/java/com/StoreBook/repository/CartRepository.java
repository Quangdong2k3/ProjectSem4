package com.StoreBook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{
    List<Cart> findByUser_Id(Long userId);
    Cart findByUser_IdAndBook_Id(Long userId,Long bookId);
    void deleteAllByUserId(Long userId);
}
