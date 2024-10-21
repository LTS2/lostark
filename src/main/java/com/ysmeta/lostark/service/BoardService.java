package com.ysmeta.lostark.service;

import com.ysmeta.lostark.entity.BoardEntity;
import com.ysmeta.lostark.entity.UserEntity;
import com.ysmeta.lostark.repository.BoardRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /* 모든 게시글 */
    public Page<BoardEntity> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return boardRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    /* 게시글 작성 */
    public boolean createPost(String title, String content, HttpSession session) {
        try {
            UserEntity user = (UserEntity) session.getAttribute("user");
            BoardEntity board = new BoardEntity();
            board.setUser(user);
            board.setTitle(title);
            board.setContent(content);
            board.setCreatedAt(LocalDateTime.now());
            boardRepository.save(board);
            return true;
        } catch (DataAccessException e) {
            log.error(">>>>> ERROR :: " + e);
            return false;
        }
    }

    public BoardEntity getPostById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    /* 게시글 수정 */
    public void updatePost(BoardEntity post) {
        post.setUpdatedAt(LocalDateTime.now());
        boardRepository.save(post);
    }

    /* 게시글 삭제 */
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}