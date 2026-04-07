package com.example.leets7th.domain.repeat.service;
import com.example.leets7th.domain.repeat.dto.RepeatResponseDto;
import org.springframework.stereotype.Service;

@Service
public class RepeatService {
    public RepeatResponseDto getRepeat(String value) {
        return new RepeatResponseDto(value,value);
    }
}
