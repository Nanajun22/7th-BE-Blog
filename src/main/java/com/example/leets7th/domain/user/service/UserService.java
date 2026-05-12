package com.example.leets7th.domain.user.service;

import com.example.leets7th.domain.auth.repository.MailCacheRepository;
import com.example.leets7th.domain.user.domain.User;
import com.example.leets7th.domain.user.domain.UserCacheRepository;
import com.example.leets7th.domain.user.domain.UserRepository;
import com.example.leets7th.domain.user.domain.UserRole;
import com.example.leets7th.domain.user.dto.UserRequestDto;
import com.example.leets7th.domain.user.dto.UserResponseDto;
import com.example.leets7th.domain.user.error.UserException;
import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.error.GlobalException;
import com.example.leets7th.global.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final JwtUtil jwtUtil;
    private final UserCacheRepository userCacheRepository;



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


    public UserResponseDto.TokenResult loginUser(UserRequestDto.Login request) {

        //로그인 검증
        User user = userRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new UserException(ErrorCode.LOGIN_ID_NOT_VALID));

        if(!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UserException(ErrorCode.PASSWORD_NOT_VALID);
        }


        //토큰 발급

        UserResponseDto.TokenResult tokens = issueToken(request.loginId(),user.getRole());


        return new UserResponseDto.TokenResult(tokens.accessToken(),tokens.refreshToken());
    }


    //토큰 재발급
    public UserResponseDto.TokenResult reissueToken(String refreshToken) {
        String loginId = userCacheRepository.getRefreshToken(refreshToken);
        if(loginId == null) {
            throw new GlobalException(ErrorCode.RT_NOT_EXISTS);
        }

        userCacheRepository.deleteRefreshToken(refreshToken);

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        return issueToken(loginId,user.getRole());
    }

    //토큰 발급
    public UserResponseDto.TokenResult issueToken(String loginId, UserRole role) {
        String accessToken = jwtUtil.generateAccessToken(loginId,role);
        String refreshToken = jwtUtil.generateRefreshToken();


        userCacheRepository.saveRefreshToken(refreshToken,loginId);

        return new UserResponseDto.TokenResult(accessToken,refreshToken);
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

