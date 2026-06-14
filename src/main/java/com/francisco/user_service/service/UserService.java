package com.francisco.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.francisco.user_service.dto.LoginRequest;
import com.francisco.user_service.dto.UserRequest;
import com.francisco.user_service.entity.User;
import com.francisco.user_service.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserRequest request){
        User user = new User();
        user.setEmail(request.email());
        String encodedPassword = passwordEncoder.encode(request.password());
        user.setPassword(encodedPassword);
    
        return userRepository.save(user);
    }
    
    public Optional<User> getUserById(UUID id){
        return userRepository.findById(id);
    }
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);

    }
    public void deleteUser(UUID id){
        userRepository.deleteById(id);


    }

    public User verifyUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // compare incoming plain text with the stored hash
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }
    


}
