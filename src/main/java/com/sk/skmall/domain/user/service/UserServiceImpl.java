package com.sk.skmall.domain.user.service;

import com.sk.skmall.domain.base.RoleType;
import com.sk.skmall.domain.user.dto.UserDTO;
import com.sk.skmall.domain.user.dto.response.SigninResponse;
import com.sk.skmall.domain.user.entity.User;
import com.sk.skmall.domain.user.repository.UserRepository;
import com.sk.skmall.domain.user.dto.request.UserSigninRequset;
import com.sk.skmall.domain.user.dto.request.UserSignupRequest;
import com.sk.skmall.util.HttpResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public SigninResponse loginProcess(UserSigninRequset request) {
        User userEntity = userRepository.findById(request.getId())
                    .orElseThrow(()-> new UsernameNotFoundException("가입이 필요한 사용자입니다."));

        if (passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            log.info("{} 로그인 했습니다.", userEntity.getUsername());
            return SigninResponse.builder()
                    .status(HttpResponses.RESPONSE_OK)
                    .id(userEntity.getId())
                    .username(userEntity.getUsername())
                    .role(userEntity.getRole())
                    .build();
        }

        return SigninResponse.builder().status(HttpResponses.RESPONSE_BAD_REQUEST).build();
    }

    @Override
    public UserDTO joinProcessOfCustomer(UserSignupRequest request) {

        validateDuplicateUser(request.getId());

        User userEntity = User.from(request);
        userEntity.setRole(RoleType.NEW_CUSTOMER);

        String rawPassword = request.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);

        User savedEntity = userRepository.save(userEntity);
        return UserDTO.from(savedEntity);
    }

    @Override
    public UserDTO joinProcessOfSeller(UserSignupRequest request) {

        validateDuplicateUser(request.getId());

        User userEntity = User.from(request);
        userEntity.setRole(RoleType.NOMAL_SELLER);

        String rawPassword = request.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);

        User savedEntity = userRepository.save(userEntity);
        return UserDTO.from(savedEntity);
    }

    @Override
    public UserDTO updateUserInfo(String newUsername, String newEmail) {
        // 사용자 존재 여부 확인
        User existingUser = userRepository.findByUsername(newUsername)
                .orElseThrow(() -> new RuntimeException("생성 가능한 username."));

        // 수정할 데이터 설정
        existingUser.updateUserInfo(newUsername, newEmail);

        // JPA의 자동 업데이트를 활용하여 데이터 수정
        return UserDTO.from(existingUser);
    }

    /**
     * username 중복 체크
     *
     * @param userId 중복 확인을 위한 파라미터
     */
    private void validateDuplicateUser(String userId) {
        boolean existingUser = userRepository.findById(userId)
                .isPresent();
        if (existingUser)
            throw new IllegalStateException("이미 가입된 회원입니다.");
    }

}
