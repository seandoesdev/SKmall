package com.sk.skmall.web;

import com.sk.skmall.domain.user.dto.MailDTO;
import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.entity.User;
import com.sk.skmall.domain.user.repository.UserRepository;
import com.sk.skmall.domain.user.service.MailService;
import com.sk.skmall.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userServiceImpl;
    private final MailService mailService;

    @PostMapping("/reset-password")
    public void resetPassword(UserDTO userDTO){
        userServiceImpl.updateUserInfo(userDTO.getPassword(), userDTO.getEmail());
    }

    @PostMapping("/mail")
    public String MailSend(@RequestParam(value="mail") String mail) {

        int verificationNum = mailService.sendMail(mail);

        return "" + verificationNum;
    }

}
