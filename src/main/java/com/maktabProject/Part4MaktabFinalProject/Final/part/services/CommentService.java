package com.maktabProject.Part4MaktabFinalProject.Final.part.services;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Comment;
import com.maktabProject.Part4MaktabFinalProject.Final.part.repository.CommentRepository;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService implements BaseService<Comment, Long> {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public Comment saveOrUpdate(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    public List<Comment> commentsOfACustomer(Long id) {
        return commentRepository.findAllByUsers_Id(id);
    }
}
