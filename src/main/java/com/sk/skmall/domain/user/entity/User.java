package com.sk.skmall.domain.user.entity;

import com.sk.skmall.domain.base.RoleType;
import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.dto.request.UserSignupRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDate birth;

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

    public static User from(UserSignupRequest request){
        return User.builder()
                .id(request.getId())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .gender(request.getGender())
                .birth(request.getBirth())
                .phone(request.getPhone())
                .terms_agree(request.getTerms_agree())
                .marketing_agree(request.getMarketing_agree())
                .build();
    }
}
