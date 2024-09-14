package com.Cristian.GraduationProjectRefactored.service;

import com.Cristian.GraduationProjectRefactored.entity.Category;
import com.Cristian.GraduationProjectRefactored.entity.Costumer;
import com.Cristian.GraduationProjectRefactored.entity.Product;
import com.Cristian.GraduationProjectRefactored.repository.CategoryRepository;
import com.Cristian.GraduationProjectRefactored.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    boolean exists(Category category) {
        return categoryRepository.existsByCategoryName(category.getCategoryName());
    }

    public void save(Category category) {
        if (exists(category)) {
            throw new EntityExistsException("Category already exists");
        }else {
            categoryRepository.save(category);
        }
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    public void update(Category updateCategory, long categoryId) {
        Category existingCategory = findById(categoryId);

        if (updateCategory != null) {
            if (updateCategory.getCategoryName() != null
                    && !existingCategory.getCategoryName().equals(updateCategory.getCategoryName())) {
                if (categoryRepository.existsByCategoryName(updateCategory.getCategoryName())) {
                    throw new EntityExistsException("Category already exists");
                }
                existingCategory.setCategoryName(updateCategory.getCategoryName());
            }

            try {
                categoryRepository.save(existingCategory);
            } catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("Error saving category: " + e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
            }
        }

        categoryRepository.save(existingCategory);

    }

    public void addExistingProductToCategory(long categoryId, long productId) {
        Category category= categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        if (!category.getProducts().contains(product)) {
            category.getProducts().add(product);
            categoryRepository.save(category);
        }
    }

    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        if (!category.getProducts().isEmpty()) {
            throw new EntityExistsException("Category already exists");
        }else {
            categoryRepository.delete(category);
        }
    }
}
