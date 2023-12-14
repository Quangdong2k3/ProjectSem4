package com.StoreBook.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.StoreBook.DTO.CartDTO;
import com.StoreBook.entity.Cart;
import com.StoreBook.entity.User;
import com.StoreBook.repository.CartRepository;

public interface CartService {
    double total(Long id);

    List<CartDTO> getCartAll(Long id);

    List<Cart> getAll(Long id);

    void deleteAllCartsByUserId(Long uid);
}

@Transactional
@Service
class implCartService implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserService userService;

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

    @Override
    public double total(Long id) {
        // TODO Auto-generated method stub
        double tong = 0.0;
        for (Cart c : cartRepository.findByUser_Id(id)) {
            tong += c.getPrice() * c.getQuantity();
        }
        return tong;
    }

    @Override
    public List<Cart> getAll(Long id) {
        // TODO Auto-generated method stub
        return cartRepository.findByUser_Id(id);
    }

    @Override
    public List<CartDTO> getCartAll(Long id) {
        // TODO Auto-generated method stub

        List<CartDTO> cartDTOs = new ArrayList<>();
        cartRepository.findByUser_Id(id).forEach(e->{
            CartDTO c = new CartDTO();
            c.setBook(e.getBook());
            c.setId(e.getId());
            c.setUser(e.getUser());
            c.setPrice(e.getPrice());
            c.setQuantity(e.getQuantity());
            cartDTOs.add(c);
        });
        return cartDTOs;
    }

    @Override
    public void deleteAllCartsByUserId(Long uid) {
        // TODO Auto-generated method stub
        cartRepository.deleteAllByUserId(uid);
    }

}
