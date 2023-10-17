package com.myblog10.controller;


import com.myblog10.entity.User;
import com.myblog10.payload.LoginDto;
import com.myblog10.payload.SignUpDto;
import com.myblog10.repository.RoleRepository;
import com.myblog10.repository.UserRepository;
import com.myblog10.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;
    //http://loclhost:8080/api/auth/signup

    @Autowired
    private AuthenticationManager authenticationManager;

    //http://localhost:8080/api/auth/signin

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto
                                                                    loginDto){
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> authenticateUser(@RequestBody SignUpDto
                                                           signUpDto) {
        Boolean emailExist = userRepo.existsByEmail(signUpDto.getEmail());
        if(emailExist){

            return new ResponseEntity<>("Email  exist",HttpStatus.BAD_REQUEST);
        }

        Boolean usernameExist = userRepo.existsByUsername(signUpDto.getUsername());
        if(usernameExist){

            return new ResponseEntity<>("Username exist",HttpStatus.BAD_REQUEST);
        }
        User user=new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode((signUpDto.getPassword())));
    userRepo.save(user);
        return new ResponseEntity<>("User is Registered",HttpStatus.CREATED);
    }
}
