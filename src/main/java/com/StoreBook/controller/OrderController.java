package com.StoreBook.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StoreBook.DTO.OrderDTO;
import com.StoreBook.entity.Cart;
import com.StoreBook.entity.Order;
import com.StoreBook.entity.User;
import com.StoreBook.service.BookService;
import com.StoreBook.service.CartService;
import com.StoreBook.service.CustomOAuth2User;
import com.StoreBook.service.EmailSenderService;
import com.StoreBook.service.MyRole;
import com.StoreBook.service.OrderItemService;
import com.StoreBook.service.OrderService;
import com.StoreBook.service.UserService;

@RestController
@RequestMapping("/BookStore/client/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;

    public Long getUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long so = 1L;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;

                MyRole myRoleUser = (MyRole) userDetails;

                so = myRoleUser.getId();
            }
            if (principal instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) principal;
                CustomOAuth2User customOAuth2User = (CustomOAuth2User) oAuth2User;
                User u = userService.findByEmail(customOAuth2User.getEmail());
                so = u.getId();
            }
        }
        return so;

        // Trả về một giá trị mặc định hoặc null tùy thuộc vào yêu cầu của bạn
    }

    @Autowired
    BookService bookService;

    @Autowired
    OrderItemService orderItemService;

    

    @PostMapping("save")
    public ResponseEntity<Object> add(@RequestBody OrderDTO orderDto) {

        Map<String, String> errors = new HashMap<>();

        for (Cart cart : cartService.getAll(getUserID())) {
            if (cart.getQuantity() > bookService.getById(cart.getBook().getId()).getQuantity()) {
                errors.put(cart.getId().toString(),
                        "Số lượng sản phẩm trong cart: " + cart.getBook().getTitle() + " Quá lớn");
            }
        }
        if (errors.isEmpty()) {
            orderDto.setUser_id(getUserID());
            Order o = orderService.add(orderDto);

            for (Cart cart : cartService.getAll(getUserID())) {
                orderItemService.add(o, cart.getBook().getId(), getUserID(), cart.getQuantity(), cart.getPrice());
            }
            cartService.deleteAllCartsByUserId(getUserID());



            System.out.println("ok");
            return ResponseEntity.ok(orderDto);
        } else {
            System.out.println("12311111111111111111111111111111");

            return ResponseEntity.badRequest().body(errors);

        }

    }
}
