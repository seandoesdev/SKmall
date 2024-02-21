package com.sk.skmall.domain.user.dto;

import com.sk.skmall.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Getter
@ToString
@AllArgsConstructor
@Builder
public class UserDTO {

    private Integer key;

    private String id;

    private String password;

    private String email;

    private String username;

    private String phone;

    private String gender;

    private LocalDateTime birth;

    private Byte terms_agree;

    private Byte marketing_agree;

    public static UserDTO toDTO(User entity){
        return UserDTO.builder()
                .key(entity.getKey())
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
