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
     void processOAuthPostLogin(String email,String fullname);
    boolean isEmailUnique(String email);

    User findByEmail(String email);
        User getByID(Long id);

}

@Service
class ImplUserService implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRep;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void add(UserDTO userDTO) {
        // TODO Auto-generated method stub
        User u = userRepository.findById(userDTO.getId()).get();

        if(u!=null){
            u.setAddress(userDTO.getAddress());
            u.setPhone(userDTO.getPhone());
        }else{
            u = new User();
           u.setEmail(userDTO.getEmail());
           if (userDTO.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            u.setPassword(encodedPassword);
        }
        u.setFullname(userDTO.getFullname()); // Lỗi ở đây
        u.getRoles().add(roleRep.findById((long)2).get()); 
        }
        
        userRepository.save(u);
    }

    @Override
    public boolean isEmailUnique(String email) {
        // TODO Auto-generated method stub
        return userRepository.existsByEmail(email);
    }
    @Override

    public void processOAuthPostLogin(String email,String fullname) {
        User existUser = userRepository.findByEmail(email);
         
        if (existUser == null) {
            User user = new User();
            user.setEmail(email);
            user.setFullname(fullname);
            user.getRoles().add(roleRep.findById((long)3).get());   
            userRepository.save(user);      
        }
         
    }

    @Override
    public User findByEmail(String email) {
        // TODO Auto-generated method stub
        return userRepository.findByEmail(email);
    }

    @Override
    public User getByID(Long id) {
        // TODO Auto-generated method stub
        return userRepository.findById(id).get();
    }  


}