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

import com.StoreBook.DTO.CartDTO;
import com.StoreBook.entity.Book;
import com.StoreBook.entity.Cart;
import com.StoreBook.entity.Customer;
import com.StoreBook.repository.BookRepository;
import com.StoreBook.repository.CartRepository;
import com.StoreBook.repository.CustomerRepository;

@RestController
@RequestMapping(value = "/BookStore/api/Cart")
public class CartController {
    
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BookRepository bookRepository;
    @GetMapping(value="getAll")
    public ResponseEntity<List<Cart>> getAllCart(){
        return new ResponseEntity<List<Cart>>(cartRepository.findByCustomer_Id((long) 1), HttpStatus.OK);
    }
    @PostMapping(value="add")
    public ResponseEntity<Cart> addCart(@RequestBody CartDTO cartDTO){

        
        Cart c = cartRepository.findByCustomer_IdAndBook_Id((long) 1, cartDTO.getBook_id());
        if(c != null){
            c.setQuantity(c.getQuantity()+cartDTO.getQuantity());
            
        }else{
           c = new Cart();
            Book b = bookRepository.findById(cartDTO.getBook_id()).get();
            Customer customer = customerRepository.findById((long)1).get();
            c.setCustomer(customer);
            c.setBook(b);
            c.setPrice(cartDTO.getPrice());
            c.setQuantity(cartDTO.getQuantity());
        }
        return new ResponseEntity<Cart>(cartRepository.save(c), HttpStatus.OK);
    }
    @DeleteMapping(value="remove/{c_id}")
    public ResponseEntity<Cart> deleteCart(@PathVariable Long c_id){
        cartRepository.deleteById(c_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping(value="clearAll")
    public ResponseEntity<Cart> deleteAllCart(){
        cartRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping(value="update")
    public ResponseEntity<Cart> editCart(@RequestBody CartDTO cartDTO){

        
        Cart c = cartRepository.findByCustomer_IdAndBook_Id((long) 1, cartDTO.getBook_id());
            c.setQuantity(cartDTO.getQuantity());
       
        return new ResponseEntity<Cart>(cartRepository.save(c), HttpStatus.OK);
    }
}
