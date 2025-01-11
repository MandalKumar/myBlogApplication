package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payloads.LoginDTO;
import com.springboot.blog.payloads.RegisterDTO;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager manager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    public AuthServiceImpl(AuthenticationManager manager, UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.manager = manager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUserNameOrEmail(), loginDTO.getPassword()));
        try {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return "User Logged-in Sucessfully !!";
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        String returnMsg = null;
        try {
            //add check for username exists in DB or not;
            if (userRepository.existsByUsername(registerDTO.getUserName())) {
                returnMsg = "User name is already exists !!";
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "User name is already exists !!");
            }
            //add check for email already exists in DB
            if (userRepository.existsByEmail(registerDTO.getEmail())) {
                returnMsg = "Email  is already exists !!";
                throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email  is already exists !!");

            }
            User user = new User();
            user.setName(registerDTO.getName());
            user.setUsername(registerDTO.getUserName());
            user.setEmail(registerDTO.getEmail());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            Set<Role> roleSet = new HashSet<>();
            Role role = roleRepository.findByName("ROLE_USER").get();
            roleSet.add(role);
            user.setRoles(roleSet);
            userRepository.save(user);
            returnMsg = "User register sucessfully !!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMsg;
    }
}
