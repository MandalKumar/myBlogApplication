package com.springboot.blog.controller;

import com.springboot.blog.payloads.PostDto;
import com.springboot.blog.payloads.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post/")
@Tag(
        name = "CRUD REST APIs for Post Resources"
)
public class PostController {
    final private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //below token is used to authenticate to use on swagger
    @Operation(
            summary = "Create POST Rest API",
            description = "Create REST API  is used to save Post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("createPost")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<PostDto>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/getAllPost")
    public ResponseEntity<PostResponse> getAllPost(
            //Adding pagination in our API
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORYBY, required = false) String sortBy,
            @RequestParam(name = "sortByAscDesc", defaultValue = AppConstants.DEFAULT_ASC_DESC, required = false) String sortByAscDesc
    ) {
        return new ResponseEntity<PostResponse>(postService.getAllPostData(pageNo, pageSize, sortBy, sortByAscDesc), HttpStatus.OK);
    }

    @Operation(
            summary = "Get POST by Id Rest API",
            description = "Get POST by Id Rest API is used to get Single  Post from the  database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getDataById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<PostDto>(postService.getDataById(id), HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/updatePost")
    public ResponseEntity<PostDto> updateDataById(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") int id) {
        return new ResponseEntity<PostDto>(postService.updatePost(postDto, id), HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/deletePost")
    public ResponseEntity deletePostById(@PathVariable(name = "id") Long id) {
        postService.deletePostById(id);
        return new ResponseEntity("Post Deleted Sucessfully !!", HttpStatus.OK);
    }

    //get Posts by categoryId
    @GetMapping("/{id}/byCategoryId")
    public ResponseEntity<List<PostDto>> getDatabyCategoryId(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<List<PostDto>>(postService.getPostByCategoryId(id), HttpStatus.OK);
    }
}
