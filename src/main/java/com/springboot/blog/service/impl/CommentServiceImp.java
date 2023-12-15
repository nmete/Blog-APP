package com.springboot.blog.service.impl;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public CommentDto createComment(long post_id, CommentDto commentDto) {
        Comment comment = mapToComment(commentDto);
        Post post = null;
        try {
            post = postRepository.findById(post_id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", post_id));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);
   }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }


    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = null;
        try {
            post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        Comment comment = null;
        try {
            comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not exist");
        }
        return mapToDto(comment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = null;
        try {
            post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        Comment comment = null;
        try {
            comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not exist");
        }
        commentRepository.delete(comment);

    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
        //return null;
        Post post = null;
        try {
            post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        Comment comment = null;
        try {
            comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not exist");
        }
        comment.setName(commentRequest.getName());
        comment.setBody(commentRequest.getBody());
        comment.setEmail(commentRequest.getEmail());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto= modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    private Comment mapToComment(CommentDto commentDto){
        Comment comment= modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
