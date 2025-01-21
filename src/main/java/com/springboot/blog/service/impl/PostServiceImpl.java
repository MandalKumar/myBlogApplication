package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payloads.CommentDTO;
import com.springboot.blog.payloads.PostDto;
import com.springboot.blog.payloads.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
@NoArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        return convertResponsePostDto(convertDTOToEntity(postDto));
    }

    @Override
    public PostResponse getAllPostData(int pageNo, int pageSize, String sortBy, String sortByAscDesc) {
        //create Pageable instance for pagination support
//        PageRequest pageable = PageRequest.of(pageNo, pageSize);
        Sort sort = sortByAscDesc.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> post = postRepository.findAll(pageable);
        //get content from page object
        List<Post> listOfPost = post.getContent();
        List<PostDto> postDtoListContent = listOfPost.stream().map(post1 -> convertResponsePostDto(post1)).collect(Collectors.toList());
        return convertPostResponse(postDtoListContent, post);
    }

    @Override
    @Transactional
    public PostDto getDataById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));
        return convertResponsePostDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));

        Category category = categoryRepository.findById(postDto.getCategoryId()).
                orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(postDto.getCategoryId())));

        post.setTitle(postDto.getTitle());
        post.setId(id);
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        Post updatedPost = postRepository.save(post);
        return convertResponsePostDto(updatedPost);
    }

    @Override
    @Transactional
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(categoryId)));
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post) -> convertResponsePostDto(post)).collect(Collectors.toList());
    }

    private PostDto convertResponsePostDto(Post newPost) {
        // Convert Entity to DTO
        PostDto newPotDto = new PostDto();
        newPotDto.setId(newPost.getId());
        newPotDto.setContent(newPost.getContent());
        newPotDto.setDescription(newPost.getDescription());
        newPotDto.setCategoryId(null != newPost.getCategory() ? newPost.getCategory().getId() : null);
        newPotDto.setTitle(newPost.getTitle()); // Convert comments if they are available
        if (newPost.getComments() != null) {
            Set<CommentDTO> commentDtos = newPost.getComments().stream().map(comment -> {
                CommentDTO commentDto = new CommentDTO();
                commentDto.setId(comment.getId());
                commentDto.setName(comment.getName());
                commentDto.setEmail(comment.getEmail());
                commentDto.setCommentBody(comment.getCommentBody());
                return commentDto;
            }).collect(Collectors.toSet());
            newPotDto.setComments(commentDtos);
        } else {
            newPotDto.setComments(null);
        }
        return newPotDto;
    }

    private Post convertDTOToEntity(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).
                orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(postDto.getCategoryId())));
        //Map DTO to Entity
        Post post = new Post();
//        post.setId(postDto.getId());
        post.setCategory(category);
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        return postRepository.save(post);
    }

    private PostResponse convertPostResponse(List<PostDto> postDtoListContent, Page<Post> post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoListContent);
        postResponse.setPageNo(post.getNumber());
        postResponse.setPageSize(post.getSize());
        postResponse.setTotalElements(post.getTotalElements());
        postResponse.setTotalpages(post.getTotalPages());
        postResponse.setLast(post.isLast());
        return postResponse;
    }
}
