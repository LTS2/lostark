package com.ysmeta.lostark.repository;

import com.ysmeta.lostark.entity.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : ejum
 * @fileName : CharacterRepository
 * @since : 8/25/24
 */
@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
}
