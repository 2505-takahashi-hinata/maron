package com.example.maron.service;

import com.example.maron.controller.form.CommentForm;
import com.example.maron.dto.UserComment;
import com.example.maron.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<UserComment> findAllComment(){
        Integer limit = 100;
        List<UserComment> comments = commentRepository.findAll(limit);
        return comments;
    }
}
