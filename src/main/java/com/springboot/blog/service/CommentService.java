package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payloads.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(long postId, CommentDTO commentDTO);

    List<CommentDTO> getAllCommentsPostById(long postId);

    CommentDTO getCommentById(long id);

    CommentDTO dataByPostAndCommentId(Long commentId, Long postId);

    CommentDTO updateByCommentAndPostId(Long commentId, Long postId, CommentDTO commentDTO);

    void deleteCommentById(Long commentId, Long postId);
}
