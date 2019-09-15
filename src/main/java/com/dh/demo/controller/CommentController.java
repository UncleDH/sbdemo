package com.dh.demo.controller;

import com.dh.demo.dto.CommentDTO;
import com.dh.demo.dto.ResultDTO;
import com.dh.demo.exception.CustomizeErrorCode;
import com.dh.demo.mapper.CommentMapper;
import com.dh.demo.model.Comment;
import com.dh.demo.model.User;
import com.dh.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping(value = "/comment")
    //public Object post(@RequestParam(name = "parentId") Long parentId) {}//原始的做法比较麻烦 下面利用spring帮助反序列化JSON
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if(user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount((long)1);
        comment.setCommentator(1);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
