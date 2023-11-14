package com.StoreBook.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.StoreBook.DTO.UserDTO;
import com.StoreBook.repository.RoleRepository;

@Controller
@RequestMapping(value = "/")
public class LoginController {
    @GetMapping(value = "login")
    public String showLogin(Authentication authentication, HttpSession session) {
        // session.setAttribute("username_id", 1);
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/BookStore/client";

        } else {
            return "Auth/login";

        }
    }

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @GetMapping("dologout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // Thực hiện xử lý đăng xuất (nếu cần)
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        // HttpSession session=request.getSession();

        // session.invalidate();
        persistentTokenRepository.removeUserTokens(authentication.getName());
        return "redirect:/";

        // Sau khi đăng xuất, chuyển hướng về trang chính hoặc trang đăng nhập
    }

    @Autowired
    private RoleRepository roleRep;

    @GetMapping(value = "register")
    public String showRegister(Model model) {
        model.addAttribute("roles", roleRep.findAll());
        model.addAttribute("user", new UserDTO());

        return "Auth/register";
    }
}
