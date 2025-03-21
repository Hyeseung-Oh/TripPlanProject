package com.TripPlan.TripPlanProject.repository;

import com.TripPlan.TripPlanProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // 회원가입
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);

    // 로그인 및 유저 정보 조회
    Optional<User> findByUserId(String userId);
}
