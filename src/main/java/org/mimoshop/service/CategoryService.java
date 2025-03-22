package org.mimoshop.service;


import org.mimoshop.exception.ResourceNotFoundException;
import org.mimoshop.model.Category;
import org.mimoshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
    public Category getCategoryByName(String name)
    {
        Category category = categoryRepository.findByNameIgnoreCase(name);
         if (category == null) {
            throw new ResourceNotFoundException("Category not found with name: " + name);
        }
        return category;
    }
    public Category createCategory(Category category) {
        // Kiểm tra xem tên category đã tồn tại chưa (nếu cần)
        if (categoryRepository.findByNameIgnoreCase(category.getName()) != null) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists.");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = getCategoryById(id); // Check and throw if not found
        //kiểm tra có trùng name với category khác không
        Category existingCategory = categoryRepository.findByNameIgnoreCase(categoryDetails.getName());
        if (existingCategory != null && !existingCategory.getId().equals(id)) {
            throw new IllegalArgumentException("Category with name '" + categoryDetails.getName() + "' already exists.");
        }
        category.setName(categoryDetails.getName());
        return categoryRepository.save(category);
    }


    public void deleteCategory(Long id) {
        Category category = getCategoryById(id); // Check and throw if not found
        categoryRepository.delete(category);
    }
    // Các phương thức khác
}