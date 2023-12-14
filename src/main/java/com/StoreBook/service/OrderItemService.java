package com.StoreBook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.StoreBook.entity.Book;
import com.StoreBook.entity.Order;
import com.StoreBook.entity.OrderItem;
import com.StoreBook.repository.OrderItemRepository;

public interface OrderItemService {
    void add(Order order, Long book_id, Long user_id, int cart_qty, double cart_price);

    List<OrderItem> getByOrderITemUser_id(Long id);

    Double subtotal(Long order_id);

    List<Book> getByHistoryBookTByUser_id(Long id);

}

@Transactional
@Service
class impOrderItemService implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRopesitory;
    @Autowired
    BookService bookService;

    @Override
    public void add(Order order, Long book_id, Long user_id, int cart_qty, double cart_price) {
        // TODO Auto-generated method stub
        OrderItem item = new OrderItem();
        item.setBook(bookService.getById1(book_id));
        item.setOrder(order);
        item.setPrice(cart_price);
        item.setQuantity(cart_qty);
        orderItemRopesitory.save(item);
    }

    @Override
    public List<OrderItem> getByOrderITemUser_id(Long id) {
        // TODO Auto-generated method stub
        System.out.println("Số phần tử" + orderItemRopesitory.findByOrder_User_Id(id).size());
        return orderItemRopesitory.findByOrder_User_Id(id);
    }

    @Override
    public Double subtotal(Long order_id) {
        // TODO Auto-generated method stub
        return orderItemRopesitory.calculateSubtotalForOrder(order_id);
    }

    @Override
    public List<Book> getByHistoryBookTByUser_id(Long id) {
        // TODO Auto-generated method stub
        return orderItemRopesitory.findDistinctBookByUser_Id(id);
    }

}
