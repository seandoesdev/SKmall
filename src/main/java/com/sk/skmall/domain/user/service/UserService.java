package com.sk.skmall.domain.user.service;

import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.dto.request.UserSigninRequset;
import com.sk.skmall.domain.user.dto.request.UserSignupRequest;
import com.sk.skmall.domain.user.dto.response.SigninResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {

    SigninResponse loginProcess(UserSigninRequset request);
    UserDTO joinProcessOfCustomer(UserSignupRequest request);
    UserDTO joinProcessOfSeller(UserSignupRequest request);
    UserDTO updateUserInfo(String newUsername, String newEmail);
//    String currentUserId();
}
