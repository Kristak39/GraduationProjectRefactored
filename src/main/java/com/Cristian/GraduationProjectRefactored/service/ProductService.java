package com.Cristian.GraduationProjectRefactored.service;

import com.Cristian.GraduationProjectRefactored.entity.Category;
import com.Cristian.GraduationProjectRefactored.entity.Product;
import com.Cristian.GraduationProjectRefactored.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public void update(Product updateProduct, Long id) {
        Product existProduct = findById(id);

        if (updateProduct != null) {
            if (updateProduct.getProductName() != null
                    && !existProduct.getProductName().equals(updateProduct.getProductName())) {
                existProduct.setProductName(updateProduct.getProductName());
            }
            if (updateProduct.getProductDescription() != null
                    && !existProduct.getProductDescription().equals(updateProduct.getProductDescription())) {
                existProduct.setProductDescription(updateProduct.getProductDescription());
            }
            if (existProduct.getProductPrice() != updateProduct.getProductPrice()
                    && existProduct.getProductPrice() >= 0) {
                existProduct.setProductPrice(updateProduct.getProductPrice());
            }
            if (existProduct.getUnitInStock() != updateProduct.getUnitInStock()
                    && existProduct.getUnitInStock() >= 0) {
                existProduct.setUnitInStock(updateProduct.getUnitInStock());
            }
        }

        productRepository.save(existProduct);
    }

    public void addUnitInStock(long productId, long quantity) {
        Product product = findById(productId);

        if(quantity>=0){
            product.setUnitInStock(product.getUnitInStock()+quantity);
        }
        update(product, productId);
    }

    public void removeUnitInStock(long productId, long quantity) {
        Product product = findById(productId);

        if(quantity>=0 && product.getUnitInStock()-quantity >=0){
            product.setUnitInStock(product.getUnitInStock()-quantity);
        }
        update(product, productId);
    }


    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
