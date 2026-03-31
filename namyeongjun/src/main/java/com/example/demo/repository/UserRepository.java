package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;


public interface UserRepository extends JpaRepository<User,Long> {


    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM users WHERE deleted_at <= :expiredTime LIMIT :batchSize",nativeQuery = true)
    int hardDeleteExpiredUser(@Param("expiredTime")LocalDateTime expiredTime,@Param("batchSize") int batchSize);
}
