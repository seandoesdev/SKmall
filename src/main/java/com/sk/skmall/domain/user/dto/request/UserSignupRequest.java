package com.sk.skmall.domain.user.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
public class UserSignupRequest {

    private String id;

    private String password;

    private String email;

    private String username;

    private String phone;

    private String gender;

    private LocalDate birth;

    private Byte terms_agree;

    private Byte marketing_agree;

}
