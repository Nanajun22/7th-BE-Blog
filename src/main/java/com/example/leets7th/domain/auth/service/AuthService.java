package com.example.leets7th.domain.auth.service;

import com.example.leets7th.domain.auth.error.AuthException;
import com.example.leets7th.domain.auth.repository.AuthCacheRepository;
import com.example.leets7th.domain.user.domain.User;
import com.example.leets7th.domain.user.domain.UserRole;
import com.example.leets7th.domain.user.dto.UserRequestDto;
import com.example.leets7th.domain.user.dto.UserResponseDto;
import com.example.leets7th.domain.user.error.UserException;
import com.example.leets7th.domain.user.service.UserService;
import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.error.GlobalException;
import com.example.leets7th.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthCacheRepository authCacheRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Transactional
    public UserResponseDto.TokenResult loginUser(UserRequestDto.Login request) {

        //로그인 검증
        User user = userService.findUserByLoginId(request.loginId())
                .orElseThrow(() -> new AuthException(ErrorCode.LOGIN_NOT_VALID));

        if(!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AuthException(ErrorCode.LOGIN_NOT_VALID);
        }


        //토큰 발급

        UserResponseDto.TokenResult tokens = issueToken(request.loginId(),user.getRole());


        return new UserResponseDto.TokenResult(tokens.accessToken(),tokens.refreshToken());
    }


    //토큰 재발급
    public UserResponseDto.TokenResult reissueToken(String refreshToken) {
        String loginId = authCacheRepository.getRefreshToken(refreshToken);
        if(loginId == null) {
            throw new GlobalException(ErrorCode.RT_NOT_EXISTS);
        }

        authCacheRepository.deleteRefreshToken(refreshToken);

        User user = userService.findUserByLoginId(loginId)
                .orElseThrow(()-> new AuthException(ErrorCode.LOGIN_NOT_VALID));

        return issueToken(loginId,user.getRole());
    }

    //토큰 발급
    private UserResponseDto.TokenResult issueToken(String loginId, UserRole role) {
        String accessToken = jwtUtil.generateAccessToken(loginId,role);
        String refreshToken = jwtUtil.generateRefreshToken();


        authCacheRepository.saveRefreshToken(refreshToken,loginId);

        return new UserResponseDto.TokenResult(accessToken,refreshToken);
    }

}
