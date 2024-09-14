package com.Cristian.GraduationProjectRefactored.controller;

import com.Cristian.GraduationProjectRefactored.entity.Category;
import com.Cristian.GraduationProjectRefactored.entity.Costumer;
import com.Cristian.GraduationProjectRefactored.entity.Product;
import com.Cristian.GraduationProjectRefactored.service.CategoryService;
import com.Cristian.GraduationProjectRefactored.service.ProductService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping("get_all")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categoryList = categoryService.findAll();
            return ResponseEntity.ok(categoryList);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable long id) {
        try {
            Category category = categoryService.findById(id);
            return ResponseEntity.ok(category);
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable long id, @RequestBody Category category) {
        try {
            categoryService.update(category,id);
            return ResponseEntity.ok("Category updated successfully");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        try {
            categoryService.save(category);
            return ResponseEntity.ok("Category created");
        }catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists");
        }
    }

    @PostMapping("/{category_id}/product/{product_id}")
    public ResponseEntity<String> addExistingProductToCategory(
            @PathVariable("category_id") long categoryId,
            @PathVariable("product_id") long productId)   {
        try {
            categoryService.addExistingProductToCategory(categoryId, productId);
            return ResponseEntity.ok("Product linked to category successfully");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{category_id}/product")
    public ResponseEntity<String> addProductToCategory(
            @PathVariable("category_id") long categoryId,
            @RequestBody Product product){
        Category category = categoryService.findById(categoryId);

        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        product.setCategory(category);

        try {
            productService.save(product);
            return ResponseEntity.ok("Product added successfully");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        try {
            categoryService.deleteById(id);
            return ResponseEntity.ok("Category deleted");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
    }

}
