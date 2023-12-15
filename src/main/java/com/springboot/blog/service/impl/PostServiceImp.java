package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public PostDto createPost(PostDto postDto)
    {
        Category category=null;
        try {
            category= categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()->
                    new ResourceNotFoundException("category", "id",postDto.getCategoryId() ));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        Post post=mapToPost(postDto);
        post.setCategory(category);
        postRepository.save(post);
        return mapToDto(post);
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

         Sort sort =sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // get content
        List<Post> listOfPost = posts.getContent();
        List<PostDto> content = listOfPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse= new PostResponse();

        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }


    @Override
    public PostDto getPostById(long id){
        Post post = null;
        try {
            post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id",id));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return mapToDto(post);

    }

    @Override
    public PostDto updatePost(PostDto dto, Long id) {
        Post post = null;
        Category category=null;
        try {
            post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id",id));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
           category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->
                    new ResourceNotFoundException("category", "id", dto.getCategoryId()));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setContent(dto.getContent());
        post.setCategory(category);
        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = null;
        try {
            post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id",id));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(long categoryId) {
        Category category=null;
        try {
            category = categoryRepository.findById(categoryId).orElseThrow(()->
                    new ResourceNotFoundException("category", "id",categoryId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Post> posts= postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post)-> mapToDto(post))
                .collect(Collectors.toList());

    }

    public PostDto mapToDto(Post post){
        PostDto postDto= modelMapper.map(post, PostDto.class);
        return postDto;
    }

    public Post mapToPost(PostDto postDto){
        Post post= modelMapper.map(postDto, Post.class);
        return post;
    }

}
