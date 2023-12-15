package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.impl.CommentServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@Tag(
        name = "REST APIs for Comment Resource"
)
@RequestMapping("/api")
public class CommentController {
    @Autowired
    CommentServiceImp commentServiceImp;

    @Operation(
            summary = "Create Comment REST API",
            description = "Create Comment APIs is use to fetch Comment to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status : 201 CREATED"
    )
    @PostMapping("/posts/{post_id}/comments")
    public ResponseEntity<CommentDto> createComment( @PathVariable long post_id,@Valid @RequestBody CommentDto commentDto){
         return new ResponseEntity<>(commentServiceImp.createComment(post_id, commentDto) , HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Comments by Post Id REST API",
            description = "Get All Comments by Post Id  REST API used to fetch list of comment associated with given post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentByPostId(@PathVariable(value = "postId") long postId){
         return new ResponseEntity <>(commentServiceImp.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get Comment by Id REST API",
            description = "Get Comment by Id REST API used to fetch single comment from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )
    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId, @PathVariable long commentId){
        return new ResponseEntity<>(commentServiceImp.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Comment REST API",
            description = "Delete Comment REST API used to delete existing comment"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public  ResponseEntity<String> deleteComment(@PathVariable long postId, @PathVariable long commentId){
        commentServiceImp.deleteComment(postId, commentId);
        return new ResponseEntity<>("comment deleted successfully", HttpStatus.OK);
    }

    @Operation(
            summary = "Update Comment by Id REST API",
            description = "Update Comment by Id REST API used to update existing comment associated with post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long postId, @PathVariable long commentId, @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentServiceImp.updateComment(postId,commentId,commentDto), HttpStatus.OK);
    }
}
