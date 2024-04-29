package com.example.Blogging.Application.Services;

import com.example.Blogging.Application.Payloads.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {   // Don't need to write public on method, bcz In interface, methods are by default public & abstract

//    Create Category
    CategoryDTO createCategory(CategoryDTO categoryDTO);

//    Update Category
    CategoryDTO updateCategory(CategoryDTO categoryDTO , Integer categoryId);

//    Delete Category
    void deleteCategory(Integer categoryId);

//    Get Category
    CategoryDTO getCategory(Integer categoryId);

//    Get all Category
    List<CategoryDTO> getAllCategories();
}
