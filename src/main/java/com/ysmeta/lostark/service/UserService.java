package com.ysmeta.lostark.service;

import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : ejum
 * @fileName : UserService
 * @since : 8/23/24
 */

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    public Optional<UserEntity> login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
