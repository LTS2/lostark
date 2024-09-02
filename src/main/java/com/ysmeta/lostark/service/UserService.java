package com.ysmeta.lostark.service;

import com.ysmeta.lostark.entity.RecruitmentEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.repository.RecruitmentRepository;
import com.ysmeta.lostark.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author : ejum
 * @fileName : UserService
 * @since : 8/23/24
 */

@Service
public class UserService {

    private static final String UPLOADED_FOLDER = "src/main/resources/static/images/";

    private final UserRepository userRepository;

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    public UserService(UserRepository userRepository, RecruitmentRepository recruitmentRepository) {
        this.userRepository = userRepository;
        this.recruitmentRepository = recruitmentRepository;
    }

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    public Optional<UserEntity> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /* 파일 저장 영역 */
    public String uploadFile(MultipartFile file) throws IOException {
        Path directoryPath = Paths.get(UPLOADED_FOLDER);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOADED_FOLDER + fileName);

        try {
            Files.write(path, file.getBytes());
            return "/images/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("파일 업로드 중 오류가 발생했습니다.");
        }
    }

    /* 파일 저장 영역 */
    public void updateUserProfileImage(Long userId, MultipartFile file) throws IOException {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        String filePath = uploadFile(file);

        String fileName = Paths.get(filePath).getFileName().toString();
        user.setPicture(filePath);
        userRepository.save(user);
    }

    /* 파일 저장 영역 */
    public String getFileName(MultipartFile file) throws IOException {
        String fullPath = uploadFile(file);
        return Paths.get(fullPath).getFileName().toString();
    }

    /* 회원이 작성한 모집글 불러오기 */
    public List<RecruitmentEntity> getRecruitList(UserEntity user) {
        return recruitmentRepository.findByUserId(user.getId());
    }
}