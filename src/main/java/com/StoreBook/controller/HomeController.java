package com.StoreBook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.StoreBook.DTO.WishList;
import com.StoreBook.entity.User;
import com.StoreBook.repository.CartRepository;
import com.StoreBook.service.BookService;
import com.StoreBook.service.CartService;
import com.StoreBook.service.CustomOAuth2User;
import com.StoreBook.service.MyRole;
import com.StoreBook.service.OrderItemService;
import com.StoreBook.service.OrderService;
import com.StoreBook.service.ReviewService;
import com.StoreBook.service.ShippingService;
import com.StoreBook.service.UserService;
import com.StoreBook.service.WishListService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/BookStore/client")
public class HomeController {

    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    @GetMapping(value = "")
    public String welcome(Model model) {
        model.addAttribute("titlePage", "BookStore");
        return "client/home";
    }
@Autowired
ReviewService reviewService;
    @GetMapping(value = "book/{id}/detail")
    public String single_product(Model model, @PathVariable Long id) {
        model.addAttribute("titlePage", "SingleProduct");
        model.addAttribute("book", bookService.getById1(id));
        return "client/single-product";
    }

    @GetMapping(value = "shop")
    public String shop_product(Model model) {
        model.addAttribute("titlePage", "Shop");
        model.addAttribute("book", bookService.getAll());
        model.addAttribute("wishList", new WishList());
        return "client/shop";
    }

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
    CartRepository cartRepository;
    @Autowired
    CartService cartService;

    @Autowired
    ShippingService shippingService;

    @GetMapping(value = "checkout")
    public String checkout(Model model) {
        model.addAttribute("titlePage", "Checkout");
        model.addAttribute("cart", cartRepository.findByUser_Id(getUserID()));
        model.addAttribute("ship", shippingService.getByUserID(getUserID()));

        return "client/checkout";
    }

    @GetMapping(value = "contact")
    public String contact(Model model) {
        model.addAttribute("titlePage", "Contact");
        return "client/contact";
    }

    @Autowired
    WishListService wishListService;

    @GetMapping(value = "mywishlist")
    public String mywishlist(Model model, @ModelAttribute("wishList") WishList wishList) {
        model.addAttribute("titlePage", "WishList");
        model.addAttribute("lstWishList", wishListService.getItems());
        return "client/wishlist";
    }

    @GetMapping(value = "mycart")
    public String mycart(Model model) {
        model.addAttribute("titlePage", "Cart");
        model.addAttribute("user_id", getUserID());
        return "client/cart";
    }

    @GetMapping(value = "about")
    public String about(Model model) {
        model.addAttribute("titlePage", "About");
        return "client/about";
    }

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @GetMapping(value = "myAccount")
    public String myAccount(Model model) {
        model.addAttribute("titlePage", "myAccount");
        model.addAttribute("user", userService.getByID(getUserID()));
        model.addAttribute("shipping", shippingService.getByUserID(getUserID()));
        model.addAttribute("order", orderService.getByUserID(getUserID()));
        model.addAttribute("orderItem", orderItemService.getByHistoryBookTByUser_id(getUserID()));

        model.addAttribute("u_id", getUserID());

        return "client/my-account";
    }
    @PostMapping(value = "order-update-status/{order_id}")
	public String updateStt( @RequestParam Long status,@PathVariable Long order_id,RedirectAttributes redirectAttributes) {
		orderService.updateStatus(status, order_id);
		redirectAttributes.addFlashAttribute("message", "Duyệt thành công!!!");
		System.out.println("STatus"+status);
		return "redirect:/BookStore/client/myAccount";
	}
}
