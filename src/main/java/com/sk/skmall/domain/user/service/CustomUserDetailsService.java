package com.sk.skmall.domain.user.service;

import com.sk.skmall.domain.user.entity.User;
import com.sk.skmall.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다. username = " + username));

        return new CustomUserDetails(userEntity);
    }

    public UserDetails loadUserByUserId(String userId) throws IllegalArgumentException {
        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다. user_id = " + userId));

        return new CustomUserDetails(userEntity);
    }

    public UserDetails loadUserByEmail(String email) throws IllegalArgumentException {
        User userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다. email = " + email));

        return new CustomUserDetails(userEntity);
    }
}
