package com.StoreBook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{
    List<Cart> findByCustomer_Id(Long customerId);
    Cart findByCustomer_IdAndBook_Id(Long customerId,Long bookId);
}
