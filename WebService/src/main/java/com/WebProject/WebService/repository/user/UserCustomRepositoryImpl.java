//package com.WebProject.WebService.repository.user;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.WebProject.WebService.entity.QUser;
//import com.WebProject.WebService.entity.User;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public class UserCustomRepositoryImpl implements UserCustomRepository {
//
//    private final JPAQueryFactory queryFactory;
//
//    public UserCustomRepositoryImpl(JPAQueryFactory queryFactory) {
//        this.queryFactory = queryFactory;
//    }
//
//    @Override
//    public List<User> searchUsers(String username, String email) {
//        return queryFactory.selectFrom(QUser.user)
//                .where(
//                        username != null ? QUser.user.username.eq(username) : null,
//                        email != null ? QUser.user.email.eq(email) : null
//                )
//                .fetch();
//    }
//
//    // 1️⃣ 특정 이름을 가진 유저 조회
//    public User findUserByUsername(String username) {
//        QUser qUser = QUser.user;
//        return queryFactory.selectFrom(qUser)
//                .where(qUser.username.eq(username))
//                .fetchOne();
//    }
//
//    // 2️⃣ 이메일이 특정 도메인을 포함하는 유저 조회
//    public List<User> findUsersByEmailDomain(String domain) {
//        QUser qUser = QUser.user;
//        return queryFactory.selectFrom(qUser)
//                .where(qUser.email.contains(domain))
//                .fetch();
//    }
//
//    // 3️⃣ 가장 나이가 많은 유저 조회
//    public User findOldestUser() {
//        QUser qUser = QUser.user;
//        return queryFactory.selectFrom(qUser)
//                .orderBy(qUser.age.desc())
//                .fetchFirst();
//    }
//}