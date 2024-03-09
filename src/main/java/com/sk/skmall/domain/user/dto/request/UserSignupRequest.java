package com.sk.skmall.domain.user.dto.request;

import com.sk.skmall.domain.base.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
public class UserSignupRequest {

    @NotBlank(message = "아이디는 필수 입력값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Pattern(regexp = "[a-zA-Z0-9]{2,9}",
            message = "아이디는 영문, 숫자만 가능하며, 2 ~ 10자리까지 가능합니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private String id;

    @NotBlank
    private String password;

    @NotBlank(message = "이메일은 필수 입력값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}",
            message = "올바르지 않은 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "실명은 필수 입력값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private String username;

    @NotBlank(message = "휴대전화번호는 필수 입력값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private String phone;

    @NotBlank(message = "성별은 필수 입력값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private String gender;

    private LocalDate birth;

    @NotBlank(message = "동의사항은 필수 입력값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private Byte terms_agree;

    @NotBlank(message = "동의사항은 필수 입력값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    private Byte marketing_agree;

}
