package com.ysmeta.lostark.repository;

import com.ysmeta.lostark.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : minjooo
 * @fileName : BoardRepository
 * @since : 2024/09/17
 */

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}