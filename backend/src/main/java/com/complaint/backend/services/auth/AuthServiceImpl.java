package com.complaint.backend.services.auth;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.complaint.backend.dtos.SignupRequest;
import com.complaint.backend.dtos.UserDTO;
import com.complaint.backend.entities.User;
import com.complaint.backend.enums.UserRole;
import com.complaint.backend.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

            @PostConstruct
            private void createAnAdminAccount() {
                Optional <User> optionalUser = userRepository.findByUserRole (UserRole. ADMIN);
                if (optionalUser.isEmpty()) {
                User newAdmin = new User();
                newAdmin.setName("Admin");
                newAdmin.setEmail("admin@test.com");
                newAdmin.setPassword(new BCryptPasswordEncoder().encode("admin"));
                newAdmin.setUserRole (UserRole.ADMIN);
                userRepository.save(newAdmin);
            } 
                else {
                        System.out.println("Admin account already exists");
                    }
            }

            @Override
            public Boolean hasUserWithEmail(String email) {
                return userRepository.findFirstByEmail(email).isPresent();
            }

            @Override
            public UserDTO signup (SignupRequest signupRequest) { 
            User user = new User();
            user.setEmail (signupRequest.getEmail());
            user.setName(signupRequest.getName ()) ;
            user.setUserRole(UserRole.EMPLOYEE) ;
            user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
            return userRepository.save(user).getUserDTO();
            
        }

}