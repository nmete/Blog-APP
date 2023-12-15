package com.springboot.blog.controller;

import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(
        name = "REST APIs for Category Resource"
)
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @Operation(
            summary = "Create Category REST API",
            description = "Create Category APIs is use to fetch Category to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status : 201 CREATED"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
          return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Category by Id REST API",
            description = "Get Category by Id REST API used to fetch single Category from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable long id){
        return new ResponseEntity<>(categoryService.getCategory(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Categories REST API",
            description = "Get All Categories REST API used to fetch list of Category from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @Operation(
            summary = "Update Category by Id REST API",
            description = "Update Category by Id REST API used to update existing Category"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategories(@RequestBody CategoryDto categoryDto,@PathVariable long id){
              return new ResponseEntity<>(categoryService.updateCategory(categoryDto, id), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Category REST API",
            description = "Delete Category REST API used to delete existing Category"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status : 200 SUCCESS"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<String> deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<String>("Category deleted successfully", HttpStatus.OK);
    }
}
