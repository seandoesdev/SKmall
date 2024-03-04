package com.sk.skmall.web.auth;

import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.service.UserService;
import com.sk.skmall.domain.user.dto.request.UserSignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sk.skmall.util.HttpResponses.RESPONSE_OK;

@Tag(name = "admin api")
@RestController
@RequiredArgsConstructor
public class JoinController {

    private final UserService userServiceImpl;

    @Operation(summary = "관리자 회원가입")
    @PostMapping("/admin/api/v1/user/admin")
    public ResponseEntity<HttpStatus> signup(@RequestBody UserSignupRequest request){
        return RESPONSE_OK;
    }

    @Operation(summary = "고객 회원가입")
    @PostMapping("/api/v1/user/customer")
    public UserDTO registerCustomer(@RequestBody UserSignupRequest request){
        System.out.println(request);
        return userServiceImpl.joinProcessOfCustomer(request);
    }

    @Operation(summary = "판매원 회원가입")
    @PostMapping("/api/v1/user/seller")
    public ResponseEntity<HttpStatus> registerSeller(@RequestBody UserSignupRequest request){
        userServiceImpl.joinProcessOfSeller(request);
        return RESPONSE_OK;
    }

}
