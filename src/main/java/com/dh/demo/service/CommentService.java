package com.dh.demo.service;

import com.dh.demo.exception.CustomizeErrorCode;
import com.dh.demo.exception.CustomizeException;
import com.dh.demo.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
    }
}