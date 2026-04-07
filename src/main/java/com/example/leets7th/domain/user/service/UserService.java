package com.example.leets7th.domain.user.service;

import com.example.leets7th.domain.user.domain.User;
import com.example.leets7th.domain.user.domain.UserRepository;
import com.example.leets7th.global.code.ErrorCode;
import com.example.leets7th.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public User getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        return user;
    }

    public String getUserName(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        return user.getName();
    }
}
