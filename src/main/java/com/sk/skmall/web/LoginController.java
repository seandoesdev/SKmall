package com.sk.skmall.web;

import com.sk.skmall.domain.base.RoleType;
import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.entity.User;
import com.sk.skmall.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.sk.skmall.util.HttpResponses.RESPONSE_OK;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userServiceImpl;

    @PostMapping("/loginForm")
    public String login(User userEntity){

        return "test all";
    }


}