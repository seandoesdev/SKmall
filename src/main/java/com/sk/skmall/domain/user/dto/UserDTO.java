package com.sk.skmall.domain.user.dto;

import com.sk.skmall.domain.base.RoleType;
import com.sk.skmall.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserDTO {

    private RoleType role;

    private String id;

    private String password;

    private String email;

    private String username;

    private String phone;

    private String gender;

    private LocalDateTime birth;

    private Byte terms_agree;

    private Byte marketing_agree;

    public static UserDTO from(User entity){
        return UserDTO.builder()
                .role(entity.getRole())
                .id(entity.getId())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .phone(entity.getPhone())
                .gender(entity.getGender())
                .birth(entity.getBirth())
                .terms_agree(entity.getTerms_agree())
                .marketing_agree(entity.getMarketing_agree())
                .build();
    }
}
