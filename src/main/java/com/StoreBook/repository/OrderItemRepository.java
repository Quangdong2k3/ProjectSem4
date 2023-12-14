package com.StoreBook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.StoreBook.entity.Book;
import com.StoreBook.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{
    
    List<OrderItem> findByOrder_User_Id(Long userId);
    
    @Query("SELECT SUM(oi.quantity * oi.price) as subtotal FROM OrderItem oi WHERE oi.order.id = :orderId")
    Double calculateSubtotalForOrder(@Param("orderId") Long orderId);
    
    @Query("SELECT DISTINCT b FROM OrderItem oi " +
        "INNER JOIN oi.book b " +
        "INNER JOIN oi.order o " +
        "INNER JOIN o.user u " +
        "WHERE u.id = :userId")
List<Book> findDistinctBookByUser_Id(@Param("userId") Long userId);
}