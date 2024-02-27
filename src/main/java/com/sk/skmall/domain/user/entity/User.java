package com.sk.skmall.domain.user.entity;

import com.sk.skmall.domain.base.RoleType;
import com.sk.skmall.domain.user.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "USER_TB")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_key")
    private Long key;

    @Setter
    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String id;

    @Setter
    @Column(name = "pw")
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

    public void updateUserInfo(String username, String password){
        this.username = username;
        this.password = password;
    }

    public static User from(UserDTO userDTO){
        return User.builder()
                .role(userDTO.getRole())
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
