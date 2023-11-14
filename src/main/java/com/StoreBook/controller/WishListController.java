package com.StoreBook.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.StoreBook.DTO.WishList;
import com.StoreBook.service.WishListService;

@RestController
@SessionAttributes("wishLists")
@RequestMapping(value = "/BookStore/client")
public class WishListController {

    @Autowired
    WishListService wishListService;

    
    
    @GetMapping(value="mywishlist/getAll")
    public ResponseEntity<List<WishList>> getAll() {
    return new ResponseEntity<>(wishListService.getItems(), HttpStatus.OK);
    }
    @PostMapping("mywishlist/add")
    public ResponseEntity<WishList> addToWishList(@RequestBody WishList w) {
        // Thêm một mục vào danh sách mong muốn
    	// System.out.println(price+"Price");
    	// System.out.println("quantity"+quantity+" book_id"+book_id);
    	
    	wishListService.addItem(w);
        return new ResponseEntity<>(w,HttpStatus.OK);
    }
    @DeleteMapping("mywishlist/remove/{id}")
    public ResponseEntity deleteToWishList(@PathVariable int id) {
        // Thêm một mục vào danh sách mong muốn
    	
    	wishListService.removeItem(id);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }
    @PutMapping("mywishlist/edit")
    public String editToWishList(@RequestBody WishList wish) {
        // Thêm một mục vào danh sách mong muốn
        
    	wishListService.editItem(wish);
        return "redirect:/BookStore/client/mywishlist";
    }
}
