package com.sk.skmall.web.auth;

import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.dto.response.SigninResponse;
import com.sk.skmall.domain.user.service.MailService;
import com.sk.skmall.domain.user.service.UserService;
import com.sk.skmall.domain.user.dto.request.UserSigninRequset;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증/인가")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userServiceImpl;
    private final MailService mailService;

    @Operation(summary = "회원 로그인")
    @PostMapping("/api/v1/auth/signin")
    @ResponseBody
    private SigninResponse signin(@RequestBody UserSigninRequset request,
                                                HttpSession httpSession){

        SigninResponse response = userServiceImpl.loginProcess(request);
        httpSession.setAttribute("loginUser", response);

        return response;
    }

    @PostMapping("/api/v1/auth/password/change")
    public void resetPassword(@RequestBody UserDTO userDTO){
        userServiceImpl.updateUserInfo(userDTO.getPassword(), userDTO.getEmail());
    }

    @PostMapping("/mail")
    public String MailSend(@RequestParam(value="mail") String mail) {

        int verificationNum = mailService.sendMail(mail);

        return "" + verificationNum;
    }

}
