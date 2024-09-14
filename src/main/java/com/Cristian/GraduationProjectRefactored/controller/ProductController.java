package com.Cristian.GraduationProjectRefactored.controller;

import com.Cristian.GraduationProjectRefactored.entity.Category;
import com.Cristian.GraduationProjectRefactored.entity.Product;
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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/get_all")
    public ResponseEntity<List<Product>> getAll(){
        try {
            List<Product> productList = productService.findAll();
            return ResponseEntity.ok(productList);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get_by_id")
    public ResponseEntity<Product> getById(Long id){
        try {
            Product product = productService.findById(id);
            return ResponseEntity.ok(product);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable long id, @RequestBody Product product) {
        try {
            productService.update(product,id);
            return ResponseEntity.ok("Product updated successfully");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody Product product) {
        try {
            productService.save(product);
            return ResponseEntity.ok("Product created");
        }catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already exists");
        }
    }

    @PutMapping("/add_unit_in_stock/{id}/{quantity}")
    public ResponseEntity<String> addUnitInStock(@PathVariable long id, @PathVariable int quantity) {
        try {
            productService.addUnitInStock(id, quantity);
            return ResponseEntity.ok("Unit added successfully");
        }catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product not found");
        }
    }

    @PutMapping("/remove_unit_in_stock/{id}/{quantity}")
    public ResponseEntity<String> removeUnitInStock(@PathVariable long id, @PathVariable int quantity) {
        try {
            productService.removeUnitInStock(id, quantity);
            return ResponseEntity.ok("Unit removed successfully");
        }catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product not found");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.ok("Product deleted");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
}
