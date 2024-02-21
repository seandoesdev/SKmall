package com.sk.skmall.domain.user.entity;

import com.sk.skmall.domain.base.RoleType;
import com.sk.skmall.domain.user.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "USER_TB")
@Getter @ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_key")
    private Integer key;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RoleType role;

    @Column(length = 16)
    private String id;

    @Setter
    @Column(name = "pw", length = 32)
    private String password;

    @Email // can be null value
    private String email;

    @Column(length = 16, unique = true)
    private String username;

    @Column(length = 1)
    private String gender;

    @CreationTimestamp
    private LocalDateTime birth;

    @Column(length = 16)
    private String phone;

    private Byte terms_agree;

    private Byte marketing_agree;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @CreationTimestamp
    @Column(name = "update_by")
    private LocalDateTime updateBy;

    @CreationTimestamp
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Builder
    public User(Integer key, String id, String email, String username,
                String password, String gender, LocalDateTime birth, String phone,
                Byte terms_agree, Byte marketing_agree) {
        this.key = key;
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.birth = birth;
        this.phone = phone;
        this.terms_agree = terms_agree;
        this.marketing_agree = marketing_agree;
    }

    public void updateUserInfo(String username, String password){
        this.username = username;
        this.password = password;
    }


    public static User toEntity(UserDTO userDTO){
        return User.builder()
                .key(userDTO.getKey())
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .gender(userDTO.getGender())
                .birth(userDTO.getBirth())
                .phone(userDTO.getPhone())
                .terms_agree(userDTO.getTerms_agree())
                .marketing_agree(userDTO.getMarketing_agree())
                .build();
    }
}
