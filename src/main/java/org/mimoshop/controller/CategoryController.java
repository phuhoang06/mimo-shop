package org.mimoshop.controller;


import jakarta.validation.Valid;
import org.mimoshop.model.Category;
import org.mimoshop.payload.request.CreateCategoryRequest;
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

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(this::convertToCategoryResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        CategoryResponse categoryResponse = convertToCategoryResponse(category);
        return  ResponseEntity.ok(categoryResponse);
    }
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest)
    {
        Category category=new Category();
        category.setName(createCategoryRequest.getName());
        Category createdCategory=categoryService.createCategory(category);
        return new ResponseEntity<>(convertToCategoryResponse(createdCategory), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long id,
                                                       @Valid @RequestBody CreateCategoryRequest categoryRequest) {

        Category category=new Category();
        category.setName(categoryRequest.getName());
        Category updatedCategory = categoryService.updateCategory(id,category);
        return new ResponseEntity<>(convertToCategoryResponse(updatedCategory), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category successfully deleted!", HttpStatus.OK);
    }

    private CategoryResponse convertToCategoryResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        return response;
    }
}