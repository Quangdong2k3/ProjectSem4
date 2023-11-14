package com.StoreBook.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.StoreBook.entity.User;
import com.StoreBook.repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        // User user = userRepository.findByUsername(username);
        // if (user == null) {
        // throw new UsernameNotFoundException("User not found");
        // }
        // // Lấy thông tin quyền từ cơ sở dữ liệu và xây dựng UserDetails
        // Set<GrantedAuthority> authorities = new HashSet<>();
        // user.getRoles().forEach(role -> authorities.add(new
        // SimpleGrantedAuthority("ROLE_" + role.getName())));

        // return new
        // org.springframework.security.core.userdetails.User(user.getUsername(),
        // user.getPassword(), authorities);
        return loadUserByEmail(username);
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // Lấy thông tin quyền từ cơ sở dữ liệu và xây dựng UserDetails

        return new MyRole(user);
    }

}
