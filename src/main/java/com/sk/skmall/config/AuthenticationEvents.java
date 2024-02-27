package com.sk.skmall.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AuthenticationEvents {
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent){
        Authentication authentication = successEvent.getAuthentication();
        log.info("Successful authentication result: {}", authentication);
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failureEvent){
        Exception e = failureEvent.getException();
        Authentication authentication = failureEvent.getAuthentication();
        log.warn("Failed authentication result: {}", authentication);
    }
}
