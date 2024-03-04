package com.sk.skmall.domain.user.dto.response;

import com.sk.skmall.domain.base.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Getter
@ToString
@AllArgsConstructor
@Builder
public class SigninResponse {

    private ResponseEntity<HttpStatus> status;
    private String id;
    private String username;
    private RoleType role;

}
