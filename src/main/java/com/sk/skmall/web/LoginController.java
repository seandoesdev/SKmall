package com.sk.skmall.web;

import com.sk.skmall.domain.base.RoleType;
import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.entity.User;
import com.sk.skmall.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userServiceImpl;

    @PostMapping("/login/{role-code}")
    public void login(@PathVariable int roleCode, User user){

    }


}
