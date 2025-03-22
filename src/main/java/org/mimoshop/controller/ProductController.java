package org.mimoshop.controller;
import jakarta.validation.Valid;
import org.mimoshop.mapper.ProductMapper;
import org.mimoshop.model.Product;
import org.mimoshop.model.Tag;
import org.mimoshop.payload.request.CreateProductRequest;
import org.mimoshop.payload.request.UpdateProductRequest;
import org.mimoshop.payload.response.ProductResponse;
import org.mimoshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper; // Inject mapper

    // Lấy tất cả sản phẩm
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> productResponses = products.stream()
                .map(productMapper::toResponse) // Sử dụng mapper
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponses);
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id); // Đã xử lý exception trong service
        ProductResponse productResponse = productMapper.toResponse(product); // Sử dụng mapper
        return ResponseEntity.ok(productResponse);
    }

    // Tạo sản phẩm mới
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        // Sử dụng mapper để convert từ request sang model
        Product product = productMapper.toEntity(request);
        Product createdProduct = productService.createProduct(product, request.getCategoryId(), request.getTagNames());
        ProductResponse productResponse = productMapper.toResponse(createdProduct);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED); // Trả về 201 Created
    }

    // Cập nhật sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
        // Sử dụng mapper
        Product productDetails = productMapper.toEntity(request);
        Product updatedProduct = productService.updateProduct(id, productDetails, request.getCategoryId(), request.getTagNames());
        ProductResponse productResponse = productMapper.toResponse(updatedProduct);
        return ResponseEntity.ok(productResponse);
    }

    // Xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id); // Đã xử lý exception trong service
        return ResponseEntity.noContent().build(); // Trả về 204 No Content
    }
}