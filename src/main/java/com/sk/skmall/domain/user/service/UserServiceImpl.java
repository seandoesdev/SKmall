package com.sk.skmall.domain.user.service;

import com.sk.skmall.domain.base.RoleType;
import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.entity.User;
import com.sk.skmall.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    @Override
    public User joinProcessOfCustomer(UserDTO userDTO) {

        validateDuplicateUser(userDTO.getUsername());

        User userEntity = User.from(userDTO);
        userEntity.setRole(RoleType.NEW_CUSTOMER);

        String rawPassword = userDTO.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);

        return userRepository.save(userEntity);
    }

    @Override
    public User joinProcessOfSeller(UserDTO userDTO) {

        validateDuplicateUser(userDTO.getUsername());

        User userEntity = User.from(userDTO);
        userEntity.setRole(RoleType.NOMAL_SELLER);

        String rawPassword = userDTO.getPassword();
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

    /**
     * username 중복 체크
     *
     * @param username 중복 확인을 위한 파라미터
     */
    private void validateDuplicateUser(String username) {
        boolean existingUser = userRepository.findByUsername(username)
                .isPresent();
        if (existingUser)
            throw new IllegalStateException("이미 가입된 회원입니다.");
    }

}
