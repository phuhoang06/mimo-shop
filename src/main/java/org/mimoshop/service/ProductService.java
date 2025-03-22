package org.mimoshop.service;


import org.mimoshop.exception.ResourceNotFoundException;
import org.mimoshop.model.Category;
import org.mimoshop.model.Product;
import org.mimoshop.model.Tag;
import org.mimoshop.repository.CategoryRepository;
import org.mimoshop.repository.ProductRepository;
import org.mimoshop.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product createProduct(Product product, Long categoryId, List<String> tagNames) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        product.setCategory(category);

        // Xử lý tags
        List<Tag> tags = getOrCreateTags(tagNames);
        product.setTags(tags);

        return productRepository.save(product);
    }
    @Transactional
    public Product updateProduct(Long id, Product productDetails, Long categoryId, List<String> tagNames) {
        Product product = getProductById(id); // Sử dụng lại getProductById để đảm bảo tìm thấy và ném ngoại lệ nếu không

        // Cập nhật thông tin cơ bản của sản phẩm
        // Kiểm tra null trước khi set
        if (productDetails.getName() != null) {
            product.setName(productDetails.getName());
        }
        if (productDetails.getDescription() != null) {
            product.setDescription(productDetails.getDescription());
        }
        if (productDetails.getPrice() != null) {
            product.setPrice(productDetails.getPrice());
        }
        if (productDetails.getImageUrl() != null) {
            product.setImageUrl(productDetails.getImageUrl());
        }

        // Cập nhật danh mục
        if(categoryId!=null){
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
            product.setCategory(category);
        }

        // Xử lý tags
        if(tagNames!=null){
            List<Tag> tags = getOrCreateTags(tagNames); // Sử dụng lại phương thức getOrCreateTags
            product.setTags(tags);
        }


        return productRepository.save(product); // Lưu các thay đổi
    }


    public void deleteProduct(Long id) {
        Product product = getProductById(id); // Check if exists and throw exception if not
        productRepository.delete(product);
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }
    public List<Product> getProductsByTags(List<String> tagNames) {
        List<Tag> tags = tagRepository.findByNameIn(tagNames);
        return productRepository.findByTagsIn(tags);
    }

    //lấy ra list tag từ list string tag name,  nếu tag chưa tồn tại thì tạo mới
    private List<Tag> getOrCreateTags(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();
        if (tagNames == null || tagNames.isEmpty()) {
            return tags; // Trả về danh sách rỗng nếu không có tagNames
        }
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                tag = new Tag();
                tag.setName(tagName);
                tag = tagRepository.save(tag); // Tạo mới và lưu tag
            }
            tags.add(tag);
        }
        return tags;
    }
    // Các phương thức khác liên quan đến logic của sản phẩm (nếu cần)
}