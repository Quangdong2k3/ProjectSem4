package com.StoreBook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StoreBook.DTO.CartDTO;
import com.StoreBook.entity.Book;
import com.StoreBook.entity.Cart;
import com.StoreBook.entity.User;
import com.StoreBook.repository.BookRepository;
import com.StoreBook.repository.CartRepository;
import com.StoreBook.repository.UserRepository;
import com.StoreBook.service.MyRole;

@RestController
@RequestMapping(value = "/BookStore/api/Cart")
public class CartController {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

    public Long getUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails instanceof MyRole) {
                return ((MyRole) userDetails).getId();
            }
        }
        return null;
    }

    @GetMapping(value = "getAll")
    public ResponseEntity<List<Cart>> getAllCart() {
        System.out.println(getUserID());
        return new ResponseEntity<List<Cart>>(cartRepository.findByUser_Id(getUserID()), HttpStatus.OK);
    }

    @PostMapping(value = "add")
    public ResponseEntity<Cart> addCart(@RequestBody CartDTO cartDTO) {

        Cart c = cartRepository.findByUser_IdAndBook_Id(getUserID(), cartDTO.getBook_id());
        if (c != null) {
            c.setQuantity(c.getQuantity() + cartDTO.getQuantity());

        } else {
            c = new Cart();
            Book b = bookRepository.findById(cartDTO.getBook_id()).get();
            User user = userRepository.findById(getUserID()).get();
            c.setUser(user);
            c.setBook(b);
            c.setPrice(cartDTO.getPrice());
            c.setQuantity(cartDTO.getQuantity());
        }
        return new ResponseEntity<Cart>(cartRepository.save(c), HttpStatus.OK);
    }

    @DeleteMapping(value = "remove/{c_id}")
    public ResponseEntity<Cart> deleteCart(@PathVariable Long c_id) {
        cartRepository.deleteById(c_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "clearAll")
    public ResponseEntity<Cart> deleteAllCart() {
        cartRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "update")
    public ResponseEntity<Cart> editCart(@RequestBody CartDTO cartDTO) {

        Cart c = cartRepository.findByUser_IdAndBook_Id(getUserID(), cartDTO.getBook_id());
        c.setQuantity(cartDTO.getQuantity());

        return new ResponseEntity<Cart>(cartRepository.save(c), HttpStatus.OK);
    }
}
