package com.sk.skmall.web;

import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userServiceImpl;

    @PostMapping("/reset-password")
    public void resetPassword(UserDTO user){
        userServiceImpl.updateUserInfo(user.getPassword(), user.getEmail());
    }

}
