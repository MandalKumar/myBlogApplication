package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payloads.CommentDTO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mapTOEntity(commentDTO);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        //set post to commentEntity
        comment.setPost(post);
        //save comment into DB
        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);
    }

    @Override
    public List<CommentDTO> getAllCommentsPostById(long postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        if (commentList.isEmpty()) {
            return (List<CommentDTO>) new ResourceNotFoundException("Post", "id", String.valueOf(postId));
        }
        return commentList.stream().map(com -> mapToDto(com)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", String.valueOf(id)));
        return mapToDto(comment);
    }

    @Override
    public CommentDTO dataByPostAndCommentId(Long commentId, Long postId) {
        //  retrive Post by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        //retrive  comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doen't belong to a perticular postId");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDTO updateByCommentAndPostId(Long commentId, Long postId, CommentDTO commentDTO) {
        //  retrive Post by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        //retrive  comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doen't belong to a perticular postId");
        }
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setCommentBody(commentDTO.getCommentBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteCommentById(Long commentId, Long postId) {
        //  retrive Post by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
        //retrive  comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doen't belong to a perticular postId");
        }
        commentRepository.delete(comment);
    }

    private CommentDTO mapToDto(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setCommentBody(comment.getCommentBody());
        dto.setEmail(comment.getEmail());
        dto.setName(comment.getName());
        return dto;
    }

    private Comment mapTOEntity(CommentDTO commDto) {
        Comment comment = new Comment();
        comment.setId(commDto.getId());
        comment.setCommentBody(commDto.getCommentBody());
        comment.setName(commDto.getName());
        comment.setEmail(commDto.getEmail());
        return comment;
    }
}
