package com.complaint.backend.controllers.auth;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.complaint.backend.dtos.AuthenticationRequest;
import com.complaint.backend.dtos.AuthenticationResponse;
import com.complaint.backend.dtos.SignupRequest;
import com.complaint.backend.dtos.UserDTO;
import com.complaint.backend.entities.User;
import com.complaint.backend.repositories.UserRepository;
import com.complaint.backend.services.auth.AuthService;
import com.complaint.backend.services.jwt.UserService;
import com.complaint.backend.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
        public class AuthController {

        private final AuthService authService;

        private final UserRepository userRepository;

        private final JwtUtil jwtUtil;

        private final UserService userService;

        private final AuthenticationManager authenticationManager;





        @PostMapping("/signup")

        public ResponseEntity <?> signup(@RequestBody SignupRequest signupRequest) {
                if (authService.hasUserWithEmail(signupRequest.getEmail())) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exist with the provided email! ");
            }

            UserDTO userDTO = authService.signup(signupRequest);
            if (userDTO == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }


        @PostMapping("/login")
        public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {

            try{

                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword())
                );
            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("Username or password is incorrect");
            }

            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
            Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
                final String jwt = jwtUtil.generateToken(userDetails);
                AuthenticationResponse response = new AuthenticationResponse();
                if (optionalUser.isPresent()) 
                {
                            response.setJwt(jwt);
                            response.setUserRole(optionalUser.get().getUserRole());
                            response.setUserId(optionalUser.get().getId());
                }
                    return response;
                }
                }
            