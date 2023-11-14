package com.StoreBook.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.StoreBook.repository.CategoryRepository;

@Controller
@RequestMapping(value="/BookStore/admin/category_book")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("titleName", "Category");
        model.addAttribute("pageName", "Category");

        return "admin/category";
    }
    
}
