package com.StoreBook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.StoreBook.DTO.ShippingDTO;
import com.StoreBook.DTO.UserDTO;
import com.StoreBook.entity.ShippingDetail;
import com.StoreBook.entity.User;
import com.StoreBook.repository.ShippingRepository;

public interface ShippingService {
    void add(ShippingDTO userDTO);

    ShippingDetail getByUserID(Long id);
}

@Service
@Transactional
class ImlShipping implements ShippingService {
    @Autowired
    private ShippingRepository repository;
    @Autowired
    UserService userService;
public Long getUserID() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long so = 0L;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                MyRole myRoleUser = (MyRole) userDetails;
                User u1 = userService.findByEmail(myRoleUser.getUsername());
                so = u1.getId();
            }
            if (principal instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) principal;
                CustomOAuth2User customOAuth2User = (CustomOAuth2User) oAuth2User;
                User u2 = userService.findByEmail(customOAuth2User.getEmail());
                so = u2.getId();
            }
        }
            return so;

        // Trả về một giá trị mặc định hoặc null tùy thuộc vào yêu cầu của bạn
    }
    @Override
    public void add(ShippingDTO userDTO) {
        // TODO Auto-generated method stub
        ShippingDetail s = repository.findByUser(userService.getByID(getUserID()));
        if (s != null) {
            s.setAddress(userDTO.getS_address());
            s.setMoreInfo(userDTO.getS_moreInfo());
            s.setPhone(userDTO.getS_phone());
            repository.save(s);

        }else{
            s = new ShippingDetail();
            s.setAddress(userDTO.getS_address());
            s.setMoreInfo(userDTO.getS_moreInfo());
            s.setPhone(userDTO.getS_phone());
            s.setUser(userService.getByID(getUserID()));
            repository.save(s);
        }

    }

    @Override
    public ShippingDetail getByUserID(Long id) {
        // TODO Auto-generated method stub

        ShippingDetail s = repository.findByUser(userService.getByID(id));

        return s == null ? new ShippingDetail() : s;
    }

}
