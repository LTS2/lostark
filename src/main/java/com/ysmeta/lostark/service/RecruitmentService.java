package com.ysmeta.lostark.service;

import com.ysmeta.lostark.entity.RecruitmentEntity;
import com.ysmeta.lostark.repository.RecruitmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : ejum
 * @fileName : RecruitmentService
 * @since : 8/25/24
 */
@Service
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    public RecruitmentService(RecruitmentRepository recruitmentRepository) {
        this.recruitmentRepository = recruitmentRepository;
    }


    // findAll() 메서드에서 List<RecruitmentEntity> 반환
    public List<RecruitmentEntity> findAll() {
        return recruitmentRepository.findAll();
    }

    public void save(RecruitmentEntity recruitmentEntity) {
        recruitmentRepository.save(recruitmentEntity);
    }
}
