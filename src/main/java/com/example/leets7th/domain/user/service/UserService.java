package com.example.leets7th.domain.user.service;

import com.example.leets7th.domain.auth.repository.MailCacheRepository;
import com.example.leets7th.domain.user.domain.User;
import com.example.leets7th.domain.user.domain.UserRepository;
import com.example.leets7th.domain.user.dto.UserRequestDto;
import com.example.leets7th.domain.user.dto.UserResponseDto;
import com.example.leets7th.domain.user.error.UserException;
import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final MailCacheRepository mailCacheRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto.UserCreateRes createUser(UserRequestDto.UserCreateReq request) {

        //인증 토큰 확인
        if(!request.verifiedToken()
                .equals(mailCacheRepository.getVerifyToken(request.email()))) {

            throw new UserException(ErrorCode.EMAIL_NOT_VERIFIED);
        }

        //인증 토큰 삭제
        mailCacheRepository.deleteVerifyToken(request.email());

        //중복 유저ID 확인
        if(userRepository.existsByLoginId(request.loginId())){
            throw new UserException(ErrorCode.LOGIN_ID_ALREADY_EXISTS);
        }

        //중복 이메일 확인
        if(userRepository.existsByEmail(request.email())) {
            throw new UserException(ErrorCode.EMAIL_ALREADY_EXISTS);

        }


        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.create(request.loginId(),
                request.name(),
                encodedPassword,
                request.email());

        userRepository.save(user);
        return UserResponseDto.UserCreateRes.from(user);
    }





    public User getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        return user;
    }

    public String getUserName(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        return user.getName();
    }
}
