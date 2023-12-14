package com.StoreBook.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.StoreBook.DTO.CartDTO;
import com.StoreBook.DTO.CustomerDTO;
import com.StoreBook.entity.Book;
import com.StoreBook.entity.Cart;
import com.StoreBook.entity.User;
import com.StoreBook.repository.BookRepository;
import com.StoreBook.repository.CartRepository;
import com.StoreBook.repository.UserRepository;
import com.StoreBook.service.CartService;
import com.StoreBook.service.CustomOAuth2User;
import com.StoreBook.service.MyRole;
import com.StoreBook.service.UserService;


@RestController
@RequestMapping(value = "/BookStore/api/cart")
public class CartController {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserService userService;
    // private void printOAuth2UserAttributes(OAuth2User oAuth2User) {
    // Map<String, Object> attributes = oAuth2User.getAttributes();

    // System.out.println("OAuth2User Attributes:");
    // for (Map.Entry<String, Object> entry : attributes.entrySet()) {
    // System.out.println(entry.getKey() + ": " + entry.getValue());
    // }
    // }
    public Long getUserID() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long so = 0L;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                MyRole myRoleUser = (MyRole) userDetails;
                User u1 = userService.findByEmail(myRoleUser.getUsername());
                so = u1.getId();
            }
            if (principal instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) principal;
                CustomOAuth2User customOAuth2User = (CustomOAuth2User) oAuth2User;
                User u2 = userService.findByEmail(customOAuth2User.getEmail());
                so = u2.getId();
            }
        }
            return so;

        // Trả về một giá trị mặc định hoặc null tùy thuộc vào yêu cầu của bạn
    }

    @GetMapping("user")
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String so = "";
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;

                MyRole myRoleUser = (MyRole) userDetails;
                // User u = userService.findByEmail(myRoleUser.getUsername());
                so = myRoleUser.getId().toString();
            }
            if (principal instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) principal;
                CustomOAuth2User customOAuth2User = (CustomOAuth2User) oAuth2User;
                User u = userService.findByEmail(customOAuth2User.getEmail());
                so = u.getId().toString();
            }
        }
            return so;

    }

    @GetMapping("Prinuser")
    public Principal user(Principal principal) {
        return principal;
    }

    @Autowired
    private CartService cartService;
    @GetMapping(value = "getAll")
    public ResponseEntity<List<CartDTO>> getAllCart() {

        return new ResponseEntity<List<CartDTO>>(cartService.getCartAll(getUserID()),HttpStatus.OK) ;
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
