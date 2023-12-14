package com.StoreBook.controller;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StoreBook.DTO.UserDTO;

import com.StoreBook.service.UserService;


import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping(value = "/api")
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "register")
    public ResponseEntity<Object> addUserRegister(@RequestBody @Valid UserDTO u, BindingResult bindingResult) {
        // Nếu có lỗi, trả về các lỗi dưới dạng JSON
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            // Nếu có lỗi, trả về các lỗi dưới dạng JSON
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            
            return ResponseEntity.badRequest().body(errors);
        }else{
            if(userService.isEmailUnique(u.getEmail())){
                errors.put("uniqueEmail","Email is exist");
                return ResponseEntity.badRequest().body(errors);
            }else{
                userService.add(u);
                // Xử lý logic khi không có lỗi
                return ResponseEntity.ok(u);
            }
            
        }
        
    }

}
