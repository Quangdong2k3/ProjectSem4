package com.StoreBook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.StoreBook.DTO.WishList;
import com.StoreBook.service.BookService;
import com.StoreBook.service.WishListService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
@RequestMapping("/BookStore/client")
public class HomeController {

    @Autowired
    BookService bookService;

    @GetMapping(value="")
    public String welcome(Model model) {
        model.addAttribute("titlePage", "BookStore");
        return "client/home";
    }
    @GetMapping(value="single-product")
    public String single_product(Model model) {
        model.addAttribute("titlePage", "SingleProduct");
        return "client/single-product";
    }
    @GetMapping(value="shop")
    public String shop_product(Model model) {
        model.addAttribute("titlePage", "Shop");
        model.addAttribute("book", bookService.getAll());
        model.addAttribute("wishList",new WishList());
        return "client/shop";
    }
    @GetMapping(value="checkout")
    public String checkout(Model model) {
        model.addAttribute("titlePage", "Checkout");
        return "client/checkout";
    }
    @GetMapping(value="contact")
    public String contact(Model model) {
        model.addAttribute("titlePage", "Contact");
        return "client/contact";
    }
    @Autowired
    WishListService wishListService;

    @GetMapping(value="mywishlist")
    public String mywishlist(Model model,@ModelAttribute("wishList") WishList wishList) {
        model.addAttribute("titlePage", "WishList");
        model.addAttribute("lstWishList", wishListService.getItems());
        return "client/wishlist";
    }
    @GetMapping(value="mycart")
    public String mycart(Model model) {
        model.addAttribute("titlePage", "Cart");
        return "client/cart";
    }
    @GetMapping(value="about")
    public String about(Model model) {
        model.addAttribute("titlePage", "About");
        return "client/about";
    }
    @GetMapping(value="myAccount")
    public String myAccount(Model model) {
        model.addAttribute("titlePage", "myAccount");
        return "client/my-account";
    }
}
