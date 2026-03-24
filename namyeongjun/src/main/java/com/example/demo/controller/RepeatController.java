package com.example.demo.controller;
import com.example.demo.dto.ErrorResponseDto;
import com.example.demo.dto.RepeatRequestDto;
import com.example.demo.dto.RepeatResponseDto;
import com.example.demo.service.RepeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class RepeatController {
    private final RepeatService repeatService;

    @GetMapping("/health")
    public ResponseEntity<Void> test() {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/string/repeat")
    public ResponseEntity<RepeatResponseDto> repeatString(@Valid @RequestBody RepeatRequestDto request) {
        return ResponseEntity.ok(repeatService.getRepeat(request.value()));
    }
}
