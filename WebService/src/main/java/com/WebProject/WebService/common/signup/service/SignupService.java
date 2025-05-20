package com.WebProject.WebService.common.signup.service;

import com.WebProject.WebService.common.signup.dto.SignupRequest;
import com.WebProject.WebService.common.signup.dto.SignupResponse;
import com.WebProject.WebService.common.signup.enums.RoleEnum;
import com.WebProject.WebService.common.signup.repository.SignupRolrRepository;
import com.WebProject.WebService.common.signup.repository.SignupUserRoleRepository;
import com.WebProject.WebService.common.signup.repository.SignupUsersRepository;
import com.WebProject.WebService.entity.Role;
import com.WebProject.WebService.entity.User;
import com.WebProject.WebService.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final PasswordEncoder passwordEncoder;
    private final SignupUsersRepository signupUsersRepository;
    private final SignupRolrRepository signupRolrRepository;
    private final SignupUserRoleRepository userRoleRepository;

    public SignupResponse signup(SignupRequest request) {
        if (signupUsersRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 유저 저장
        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .build();
        signupUsersRepository.save(user);

        // 권한 조회 or 생성
        Role role = signupRolrRepository.findByName(RoleEnum.NORMAL)
                .orElseThrow(() -> new IllegalStateException("기본 권한이 존재하지 않습니다. DB에 ROLE을 먼저 넣어주세요."));


        // 유저 권한 저장
        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role)
                .build();
        userRoleRepository.save(userRole);

        return new SignupResponse("회원가입이 완료되었습니다.");
    }
}
