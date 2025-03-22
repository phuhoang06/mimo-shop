package org.mimoshop.service;


import org.mimoshop.model.Category;
import org.mimoshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class SearchService {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    public List<Product> searchProducts(String query, String searchType) {
        if ("name".equalsIgnoreCase(searchType)) {
            return productService.searchProductsByName(query);
        } else if ("category".equalsIgnoreCase(searchType)) {
            try{
                Category category = categoryService.getCategoryByName(query);
                return  category.getProducts();
            }
            catch (Exception e)
            {
                return Collections.emptyList(); // Return an empty list instead of null
            }
        }
        else if ("tag".equalsIgnoreCase(searchType)) { // Thêm tìm kiếm theo tag
            return productService.getProductsByTags(Collections.singletonList(query));
        }
        // Thêm các loại tìm kiếm khác nếu cần (ví dụ: theo tag)
        return Collections.emptyList(); // Trả về danh sách rỗng nếu không tìm thấy hoặc loại tìm kiếm không hợp lệ

    }
}
