package com.StoreBook.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.StoreBook.DTO.OrderDTO;
import com.StoreBook.DTO.ShippingDTO;
import com.StoreBook.entity.Cart;
import com.StoreBook.entity.Order;
import com.StoreBook.entity.User;
import com.StoreBook.repository.OrderRepository;

public interface OrderService {
    boolean checkQuantity(Cart cart);

    Order add(OrderDTO dto);

    List<Order> getByUserID(Long id);

    List<Order> getAll();

    void updateStatus(Long status_id, Long order_id);
}

@Transactional
@Service
class implOrderService implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    EmailSenderService emailSenderService;

    @Override
    public boolean checkQuantity(Cart cart) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkQuantity'");
    }

    @Autowired
    ShippingService shippingService;

    @Override
    public Order add(OrderDTO dto) {
        // TODO Auto-generated method stub
        Order o = new Order();
        o.setOrder_date(new Date());
        o.setStatus(1);
        o.setTotal_amount(cartService.total(dto.getUser_id()));
        User u = userService.getByID(dto.getUser_id());
        o.setUser(u);

        ShippingDTO s = new ShippingDTO();
        s.setS_user_id(dto.getUser_id());
        s.setS_address(dto.getAddress());
        s.setS_phone(dto.getPhone());
        s.setS_moreInfo(dto.getNote());
        shippingService.add(s);

        String sub = u.getFullname().equals("") ? u.getEmail() : u.getFullname();
        String htmlBody = "<html><head><style>"
                + "body { background-color: #f2f2f2; padding: 20px; }"
                + "h1 { color: #333333; }"
                + "p { font-size: 16px; line-height: 1.5; color: #666666; }"
                + ".signature { font-size: 14px; color: #888888; }"
                + "</style></head>"
                + "<body>"
                + "<h1>Hello, " + u.getFullname() + "!</h1>"
                + "<p>Chúng tôi sẽ giao hàng cho bạn trong vòng 2-3 ngày!!.</p>"
                + "<p class='signature'>Regards, BookStoreApplication</p>"
                + "</body></html>";
        emailSenderService.sendEmail(u.getEmail(), "Lời cảm ơn tới " + sub, htmlBody);

        return orderRepository.save(o);
    }

    @Override
    public List<Order> getAll() {
        // TODO Auto-generated method stub
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getByUserID(Long id) {
        // TODO Auto-generated method stub
        return orderRepository.findByUser_Id(id);
    }

    @Override
    public void updateStatus(Long status_id, Long order_id) {
        // TODO Auto-generated method stub

        Order o = orderRepository.findById(order_id).get();

        // Nếu mà status = 0 -> Hủy đơn hàng

        if (status_id == 0) {
            o.setStatus(0);
        }

        // -> Duyệt đơn hàng
        if (status_id == 1) {
            o.setStatus(1);
        }

        // -> Đóng hàng
        if (status_id == 2) {
            o.setStatus(2);
        }
        // ->Giao hàng
        if (status_id == 3) {
            o.setStatus(3);
        }

        // ->xong
        if (status_id == 4) {
            o.setStatus(4);
        }
if (status_id == 5) {
            o.setStatus(5);
        }
        orderRepository.save(o);
    }

}
