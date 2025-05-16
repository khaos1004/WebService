//package com.WebProject.WebService.service;
//
//import com.WebProject.WebService.entity.User;
//import com.WebProject.WebService.repository.user.UserRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//
//@Service
//@Transactional
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    // 1️⃣ ID로 유저 찾기
//    public User findUserById(Long id) {
//        return userRepository.findById(id).orElse(null);
//    }
//
//    // 2️⃣ 특정 나이 이상인 유저 조회
//    public List<User> findUsersByAgeGreaterThan(int age) {
//        return userRepository.findAll().stream()
//                .filter(user -> user.getAge() > age)
//                .toList();
//    }
//
//    // 3️⃣ 유저 데이터 추가
//    public User saveUser(User user) {
//        return userRepository.save(user);
//    }
//}
