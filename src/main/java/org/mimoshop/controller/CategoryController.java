package org.mimoshop.controller;


import jakarta.validation.Valid;
import org.mimoshop.mapper.CategoryMapper;
import org.mimoshop.model.Category;
import org.mimoshop.payload.request.CreateCategoryRequest;
import org.mimoshop.payload.request.UpdateCategoryRequest;
import org.mimoshop.payload.response.CategoryResponse;
import org.mimoshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper; // Inject

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(categoryMapper::toResponse) // Dùng mapper
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        CategoryResponse categoryResponse = categoryMapper.toResponse(category); // Dùng mapper
        return ResponseEntity.ok(categoryResponse);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        Category category = categoryMapper.toEntity(request); // Dùng mapper
        Category createdCategory = categoryService.createCategory(category);
        CategoryResponse categoryResponse = categoryMapper.toResponse(createdCategory);
        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @Valid @RequestBody UpdateCategoryRequest request) {
        Category categoryDetails = categoryMapper.toEntity(request); // Dùng mapper
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
        CategoryResponse categoryResponse = categoryMapper.toResponse(updatedCategory);
        return ResponseEntity.ok(categoryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}