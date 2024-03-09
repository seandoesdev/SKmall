package com.sk.skmall.domain.user.dto.request;

import com.sk.skmall.domain.base.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserSigninRequset {

    @NotBlank(message = "아이디는 필수 입력값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Pattern(regexp = "[a-zA-Z0-9]{2,9}",
            message = "아이디는 영문, 숫자만 가능하며 2 ~ 10자리까지 가능합니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.", groups = ValidationGroups.NotEmptyGroup.class)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}",
            message = "비밀번호는 영문과 숫자 조합으로 8 ~ 16자리까지 가능합니다.", groups = ValidationGroups.PatternCheckGroup.class)
    private String password;
}
