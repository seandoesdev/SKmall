package com.sk.skmall.domain.user.service;

import com.sk.skmall.domain.base.RoleType;
import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.entity.User;
import com.sk.skmall.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    @Override
    public User joinProcessOfCustomer(UserDTO user) {

        userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("고객을 찾을 수 없습니다."));

        User userEntity = User.toEntity(user);
        userEntity.setRole(RoleType.NEW_CUSTOMER);


        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);

        return userRepository.save(userEntity);
    }

    @Override
    public User joinProcessOfSeller(UserDTO user) {

        userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("판매자를 찾을 수 없습니다."));

        User userEntity = User.toEntity(user);
        userEntity.setRole(RoleType.NOMAL_SELLER);

        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);

        return userRepository.save(userEntity);
    }

    @Override
    public User updateUserInfo(String newUsername, String newEmail) {
        // 사용자 존재 여부 확인
        User existingUser = userRepository.findByUsername(newUsername)
                .orElseThrow(() -> new RuntimeException("생성 가능한 username."));

        // 수정할 데이터 설정
        existingUser.updateUserInfo(newUsername, newEmail);

        // JPA의 자동 업데이트를 활용하여 데이터 수정
        return existingUser;
    }

    public HashMap<String, String> verify(){
        HashMap<String, String> userMap = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String id = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        userMap.put("id", id);
        userMap.put("role", role);

        return userMap;
    }

}
