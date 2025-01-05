package com.springboot.blog.controller;

import com.springboot.blog.payloads.CommentDTO;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/comment/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("post/{postId}/comments")

    public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postId") long postId,
                                                    @Valid @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("{postId}/getAllComments")
    public ResponseEntity<List<CommentDTO>> getAllDataByPostId(@PathVariable(name = "postId") long postId) {
        return new ResponseEntity<List<CommentDTO>>(commentService.getAllCommentsPostById(postId), HttpStatus.OK);
    }

    @GetMapping("{commentId}/getComment")
    public ResponseEntity<CommentDTO> getByCommentId(@PathVariable(name = "commentId") long id) {
        return new ResponseEntity<CommentDTO>(commentService.getCommentById(id), HttpStatus.OK);
    }

    @GetMapping("{commentId}/getComment/{postId}")
    public ResponseEntity<CommentDTO> getByPostIdAndCommentId(@PathVariable(name = "commentId") long id,
                                                              @PathVariable(name = "postId") Long postId) {
        return new ResponseEntity<CommentDTO>(commentService.dataByPostAndCommentId(id, postId), HttpStatus.OK);
    }

    @PutMapping("{commentId}/updateComment/{postId}")
    public ResponseEntity<CommentDTO> updateComment(@Valid @PathVariable(name = "commentId") long id,
                                                    @PathVariable(name = "postId") Long postId, @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<CommentDTO>(commentService.updateByCommentAndPostId(id, postId, commentDTO), HttpStatus.OK);

    }

    @DeleteMapping("{commentId}/deleteComment/{postId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "commentId") long id,
                                                @PathVariable(name = "postId") Long postId) {
        commentService.deleteCommentById(id, postId);
        System.out.println("Hii test my changes!!");
        return new ResponseEntity<String>("Data Deleted Sucessfully!!", HttpStatus.OK);
    }
}
