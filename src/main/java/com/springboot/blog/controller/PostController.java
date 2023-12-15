package com.springboot.blog.controller;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "REST APIs for Post Resource"
)
public class PostController {

    @Autowired
    PostService postService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create post REST API",
            description = "Create post APIs is use to fetch post to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status : 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bearer authentications"
    )
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto post){
        postService.createPost(post);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Post REST API",
            description = "Get All Post REST API used to fetch list of post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )
    @GetMapping
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNo" , defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy" , defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value ="sortDir" , defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir
            ){
           return new ResponseEntity<>(postService.getAllPost(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @Operation(
            summary = "Get Post by Id REST API",
            description = "Get Post by Id REST API used to fetch single post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable  long id)  {
            return ResponseEntity.ok(postService.getPostById(id));

    }

    @Operation(
            summary = "Update Post by Id REST API",
            description = "Update Post by Id REST API used to update existing post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )
    @PutMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentications"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable long id){
        return new ResponseEntity<>(postService.updatePost(postDto,id),HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API used to delete existing post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bearer authentications"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePost(id);
        return new ResponseEntity<>("post deleted successfully", HttpStatus.OK);
    }

    //posts by categories
    @Operation(
            summary = "Get Post by Category Id REST API",
            description = "Get Post by Category Id REST API used to get post with given category"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable("id") long categoryId){
        return new ResponseEntity<>(postService.getPostsByCategory(categoryId), HttpStatus.OK);
    }
}
