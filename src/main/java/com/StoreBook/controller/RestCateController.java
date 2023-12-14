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

import com.StoreBook.DTO.CategoryDTO;

import com.StoreBook.service.CategoryService;

@RestController
@RequestMapping("/category")
public class RestCateController {
    @Autowired
    CategoryService categoryService;


    @GetMapping("list")
    public ResponseEntity <Object> getData(){
        List<CategoryDTO> dataList = categoryService.getAll();
        return new ResponseEntity<>(dataList,HttpStatus.OK); // Trả về dữ liệu dưới dạng JSON
    }
    @PostMapping("create")
    public ResponseEntity<?>  add(@RequestBody CategoryDTO categoryDTO){
        categoryService.add(categoryDTO);
        return new ResponseEntity<>(HttpStatus.OK); // Trả về dữ liệu dưới dạng JSON
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody CategoryDTO ca){
        categoryService.update(ca);
        return new ResponseEntity<>(HttpStatus.OK); // Trả về dữ liệu dưới dạng JSON
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(name="id") int id){
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK); // Trả về dữ liệu dưới dạng JSON
    }
    @GetMapping("details/{id}")
    public ResponseEntity<?> getById(@PathVariable(name="id") int id){
        CategoryDTO c = categoryService.getById(id);
        return new ResponseEntity<>(c,HttpStatus.OK); // Trả về dữ liệu dưới dạng JSON
    }
}
