package com.example.Blogging.Application.Controllers;

import com.example.Blogging.Application.Payloads.ApiResponse;
import com.example.Blogging.Application.Payloads.CategoryDTO;
import com.example.Blogging.Application.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    create
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO createCategory = this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createCategory , HttpStatus.CREATED);
    }

//    update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO ,@PathVariable Integer categoryId){
        CategoryDTO updateCategory = this.categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(updateCategory , HttpStatus.OK);
    }

//    delete
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully !!" , true), HttpStatus.OK);
    }

//    get
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer categoryId){
        CategoryDTO categoryDTO = this.categoryService.getCategory(categoryId);
        return new ResponseEntity<>(categoryDTO , HttpStatus.OK);
    }


//    getAll
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> allCategories = this.categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

}
