package com.springboot.blog.service;

import com.springboot.blog.payloads.LoginDTO;
import com.springboot.blog.payloads.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);
}
