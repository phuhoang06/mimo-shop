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
    private ProductMapper productMapper; // Inject ProductMapper

    // Lấy tất cả sản phẩm
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> productResponses = products.stream()
                .map(productMapper::toResponse) // Sử dụng ProductMapper
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponses);
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductResponse productResponse = productMapper.toResponse(product); // Sử dụng ProductMapper
        return ResponseEntity.ok(productResponse);
    }

    // Tạo sản phẩm mới
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        Product product = convertToProduct(request);
        Product createdProduct = productService.createProduct(product, request.getCategoryId(), request.getTagNames());
        ProductResponse productResponse = productMapper.toResponse(createdProduct); // Sử dụng ProductMapper
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    // Cập nhật sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
        Product productDetails = convertToProduct(request);
        Product updatedProduct = productService.updateProduct(id, productDetails, request.getCategoryId(), request.getTagNames());
        ProductResponse productResponse = productMapper.toResponse(updatedProduct); //Sử dụng ProductMapper
        return ResponseEntity.ok(productResponse);
    }

    // Xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id); // Đã xử lý exception trong service
        return ResponseEntity.noContent().build(); // Trả về 204 No Content
    }

    // Phương thức chuyển đổi từ Product (model) sang ProductResponse (DTO)
    private ProductResponse convertToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setImageUrl(product.getImageUrl());
        response.setCategoryId(product.getCategory().getId());
        response.setCategoryName(product.getCategory().getName());
        // Convert list<Tag> -> List<String> tag name
        if(product.getTags() != null) {
            List<String> tagNames = product.getTags().stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList());
            response.setTagNames(tagNames);
        }
        return response;
    }

    // Phương thức chuyển đổi từ CreateProductRequest/UpdateProductRequest sang Product (model)
    private Product convertToProduct(Object request) { // Dùng Object để dùng chung cho cả 2 loại request
        Product product = new Product();
        if (request instanceof CreateProductRequest) {
            CreateProductRequest createRequest = (CreateProductRequest) request;
            product.setName(createRequest.getName());
            product.setDescription(createRequest.getDescription());
            product.setPrice(createRequest.getPrice());
            product.setImageUrl(createRequest.getImageUrl());
        } else if (request instanceof UpdateProductRequest) {
            UpdateProductRequest updateRequest = (UpdateProductRequest) request;
            product.setName(updateRequest.getName());
            product.setDescription(updateRequest.getDescription());
            product.setPrice(updateRequest.getPrice());
            product.setImageUrl(updateRequest.getImageUrl());
        }
        return product;
    }
}