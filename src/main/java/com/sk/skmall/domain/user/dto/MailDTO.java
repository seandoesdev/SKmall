package com.sk.skmall.domain.user.dto;

import lombok.Data;

@Data
public class MailDTO {

    private String receiver;
    private String title;
    private String context;
}
