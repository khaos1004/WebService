package com.WebProject.WebService.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.WebProject.WebService.entity.QUser;
import com.WebProject.WebService.entity.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public UserQueryDslRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    // 1️⃣ 특정 이름을 가진 유저 조회
    public User findUserByUsername(String username) {
        QUser qUser = QUser.user;
        return queryFactory.selectFrom(qUser)
                .where(qUser.username.eq(username))
                .fetchOne();
    }

    // 2️⃣ 이메일이 특정 도메인을 포함하는 유저 조회
    public List<User> findUsersByEmailDomain(String domain) {
        QUser qUser = QUser.user;
        return queryFactory.selectFrom(qUser)
                .where(qUser.email.contains(domain))
                .fetch();
    }

    // 3️⃣ 가장 나이가 많은 유저 조회
    public User findOldestUser() {
        QUser qUser = QUser.user;
        return queryFactory.selectFrom(qUser)
                .orderBy(qUser.age.desc())
                .fetchFirst();
    }
}