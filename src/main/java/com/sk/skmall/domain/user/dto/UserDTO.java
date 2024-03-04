package com.sk.skmall.domain.user.dto;

import com.sk.skmall.domain.base.RoleType;
import com.sk.skmall.domain.user.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {

    private RoleType role;

    private String id;

    private String password;

    private String email;

    private String username;

    private String phone;

    private String gender;

    private LocalDate birth;

    private Byte terms_agree;

    private Byte marketing_agree;

    public UserDTO(String id, String password, String email, String username, String phone, String gender, LocalDate birth, Byte terms_agree, Byte marketing_agree) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.gender = gender;
        this.birth = birth;
        this.terms_agree = terms_agree;
        this.marketing_agree = marketing_agree;
    }

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
