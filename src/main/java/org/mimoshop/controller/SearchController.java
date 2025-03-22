package org.mimoshop.controller;


import org.mimoshop.mapper.ProductMapper;
import org.mimoshop.model.Product;
import org.mimoshop.payload.response.ProductResponse;
import org.mimoshop.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam("query") String query,
            @RequestParam("type") String searchType) {

        List<Product> products = searchService.searchProducts(query, searchType);
        List<ProductResponse> productResponses = products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponses);
    }
}