package com.ysmeta.lostark.repository;

import com.ysmeta.lostark.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : ejum
 * @fileName : UserRepository
 * @since : 8/23/24
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);
}
