package com.StoreBook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StoreBook.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{
    List<Order> findByUser_Id(Long userId);
}
