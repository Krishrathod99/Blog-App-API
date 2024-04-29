package com.example.Blogging.Application.Services.implementation;

import com.example.Blogging.Application.Entities.Category;
import com.example.Blogging.Application.Exceptions.ResourceNotFoundException;
import com.example.Blogging.Application.Payloads.CategoryDTO;
import com.example.Blogging.Application.Repositories.CategoryRepository;
import com.example.Blogging.Application.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;      // modelMapper for convert Category to CategoryDTO and CategoryDTO to Category
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
//        we want to add category in database but here we got CategoryDTO, so we convert it
        Category category = this.modelMapper.map(categoryDTO, Category.class);    // convert dto to category
        Category savedCat = this.categoryRepository.save(category);
        return this.modelMapper.map(savedCat ,CategoryDTO.class);                         // return  category to dto

    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category" , "Category Id", categoryId));

        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        Category updatedCategory = this.categoryRepository.save(category);

        return this.modelMapper.map(updatedCategory , CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));

        this.categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO getCategory(Integer categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));

        return this.modelMapper.map(category , CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = this.categoryRepository.findAll();

//        convert each category of map into categoryDTO
        List<CategoryDTO> categoryDTOS = categories.stream().map((category) -> this.modelMapper.map(category, CategoryDTO.class)).toList();
        return categoryDTOS;
    }
}
