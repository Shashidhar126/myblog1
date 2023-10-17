package com.myblog10.repository;

import com.myblog10.entity.Comment;
import com.myblog10.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(long postId );
}
