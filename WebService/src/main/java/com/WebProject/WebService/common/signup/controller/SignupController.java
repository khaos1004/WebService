package com.WebProject.WebService.common.signup.controller;

import com.WebProject.WebService.common.signup.dto.SignupRequest;
import com.WebProject.WebService.common.signup.dto.SignupResponse;
import com.WebProject.WebService.common.signup.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SignupController {
    private final SignupService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok(new SignupResponse("회원가입 완료"));
    }
}
