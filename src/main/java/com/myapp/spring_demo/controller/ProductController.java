package com.myapp.spring_demo.controller;

import com.myapp.spring_demo.entity.Product;
import com.myapp.spring_demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/status")
    public String getAlStatus() {
        return "Application running..!";
    }

    @GetMapping("/products")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping("/products")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(product));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
