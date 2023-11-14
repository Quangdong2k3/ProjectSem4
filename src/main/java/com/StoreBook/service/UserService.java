package com.StoreBook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.StoreBook.entity.User;
import com.StoreBook.DTO.UserDTO;
import com.StoreBook.repository.RoleRepository;
import com.StoreBook.repository.UserRepository;

public interface UserService {
    void add(UserDTO userDTO);

    boolean isEmailUnique(String email);
}

@Service
class UserImplService implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRep;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void add(UserDTO userDTO) {
        // TODO Auto-generated method stub
        User u = new User();
        u.setEmail(userDTO.getEmail());
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        u.setPassword(encodedPassword);
        u.setFullname(userDTO.getFullname()); // Lỗi ở đây
        u.getRoles().add(roleRep.findById(userDTO.getRole_id()).get());
        userRepository.save(u);
    }

    @Override
    public boolean isEmailUnique(String email) {
        // TODO Auto-generated method stub
        return userRepository.existsByEmail(email);
    }
}