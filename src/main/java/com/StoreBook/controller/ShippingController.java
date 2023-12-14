package com.StoreBook.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StoreBook.DTO.ShippingDTO;
import com.StoreBook.DTO.UserDTO;
import com.StoreBook.entity.User;
import com.StoreBook.service.CustomOAuth2User;
import com.StoreBook.service.MyRole;
import com.StoreBook.service.ShippingService;
import com.StoreBook.service.UserService;

@RestController
@RequestMapping("/BookStore/client/account")
public class ShippingController {
    @Autowired
    ShippingService shippingService;
    @Autowired
    UserService userService;

    public Long getUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long so = 0L;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof MyRole) {
                MyRole myRoleUser = (MyRole) principal;
                so = myRoleUser.getId();
            } else if (principal instanceof CustomOAuth2User) {
                CustomOAuth2User oAuth2User = (CustomOAuth2User) principal;
                User u = userService.findByEmail(oAuth2User.getEmail());
                so = u.getId();
            }
        }
        return so;

        // Trả về một giá trị mặc định hoặc null tùy thuộc vào yêu cầu của bạn
    }

    @PutMapping("editShipping")
    public ResponseEntity<Object> addInfoShipping(@RequestBody @Valid ShippingDTO s, BindingResult bindingResult) {
        // Nếu có lỗi, trả về các lỗi dưới dạng JSON
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            // Nếu có lỗi, trả về các lỗi dưới dạng JSON
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errors);
        } else {
            s.setS_user_id(getUserID());
            shippingService.add(s);
            return ResponseEntity.ok(s);

        }

    }
    @PutMapping("editUserInfo")
    public ResponseEntity<Object> addInfoUser(@RequestBody UserDTO u, BindingResult bindingResult) {
        // Nếu có lỗi, trả về các lỗi dưới dạng JSON
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            // Nếu có lỗi, trả về các lỗi dưới dạng JSON
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errors);
        } else {
            u.setId(getUserID());
            userService.add(u);
            return ResponseEntity.ok(u);

        }

    }
}
