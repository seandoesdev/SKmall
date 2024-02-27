package com.sk.skmall.web;

import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sk.skmall.util.HttpResponses.RESPONSE_OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class JoinController {

    private final UserService userServiceImpl;

    @PostMapping("/customer")
    public ResponseEntity<HttpStatus> registerCustomer(UserDTO userDTO){
        userServiceImpl.joinProcessOfCustomer(userDTO);
        return RESPONSE_OK;
    }

    @PostMapping("/seller")
    public ResponseEntity<HttpStatus> registerSeller(UserDTO userDTO){
        userServiceImpl.joinProcessOfSeller(userDTO);
        return RESPONSE_OK;
    }
}
