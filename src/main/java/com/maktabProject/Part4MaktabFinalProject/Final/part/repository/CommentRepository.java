package com.maktabProject.Part4MaktabFinalProject.Final.part.repository;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByUsers_Id(Long id);
}
