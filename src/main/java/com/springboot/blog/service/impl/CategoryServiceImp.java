package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category= modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(long categoryId) {
        Category category=null;
        try {
            category= categoryRepository.findById(categoryId)
          .orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        CategoryDto categoryDto= modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories =  categoryRepository.findAll();

        return categories.stream().map((category1)-> modelMapper.map(category1, CategoryDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long categoryId) {
        Category category=null;
        try {
            category= categoryRepository.findById(categoryId)
                    .orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category=null;
        try {
            category= categoryRepository.findById(categoryId)
                    .orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        categoryRepository.delete(category);
    }
}
