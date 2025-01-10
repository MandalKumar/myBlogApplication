package com.springboot.blog.service;

import com.springboot.blog.payloads.PostDto;
import com.springboot.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPostData(int pageNo, int pageSize,String sortBy,String sortByAscDesc);

    PostDto getDataById(Long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(Long id);
}
