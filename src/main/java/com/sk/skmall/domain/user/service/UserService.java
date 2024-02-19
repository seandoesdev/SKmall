package com.sk.skmall.domain.user.service;

import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.entity.User;

public interface UserService {

    User joinProcess(UserDTO user, int roleCode);
    User updateUserInfo(String newUsername, String newEmail);
//    String currentUserId();
}
