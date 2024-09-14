package com.Cristian.GraduationProjectRefactored.controller;

import com.Cristian.GraduationProjectRefactored.entity.Address;
import com.Cristian.GraduationProjectRefactored.entity.Product;
import com.Cristian.GraduationProjectRefactored.entity.Supplier;
import com.Cristian.GraduationProjectRefactored.service.AddressService;
import com.Cristian.GraduationProjectRefactored.service.ProductService;
import com.Cristian.GraduationProjectRefactored.service.SupplierService;
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
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @Autowired
    AddressService addressService;
    @Autowired
    private ProductService productService;

    @GetMapping("/get_all")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        try {
            List<Supplier> supplierList = supplierService.findAll();
            return new ResponseEntity<>(supplierList, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable long id) {
        try {
            Supplier supplier = supplierService.findById(id);
            return new ResponseEntity<>(supplier, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSupplier(@PathVariable long id, @RequestBody Supplier supplier) {
        try {
            supplierService.update(supplier,id);
            return ResponseEntity.ok("Supplier updated successfully");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
        }catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody Supplier supplier) {
        try {
            supplierService.save(supplier);
            return ResponseEntity.ok("Supplier created");
        }catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Supplier already exists");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        try {
            supplierService.deleteById(id);
            return ResponseEntity.ok("Supplier deleted");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
        }
    }

    @PostMapping("/{supplier_id}/addresses/{address_id}")
    public ResponseEntity<String> addExistingAddressToSupplier(
            @PathVariable("supplier_id") long supplierId,
            @PathVariable("address_id") long addressId) {
        try {
            supplierService.addExistingAddressToSupplier(supplierId, addressId);
            return ResponseEntity.ok("Address linked to supplier successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{supplier_id}/addresses")
    public ResponseEntity<String> addAddressToSupplier(
            @PathVariable("supplier_id") long supplierId,
            @RequestBody Address address) {
        Supplier supplier = supplierService.findById(supplierId);
        if (supplier == null) {
            return ResponseEntity.notFound().build();
        }

        address.setSupplier(supplier);

        try {
            addressService.save(address);
            return ResponseEntity.ok("Address added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{supplier_id}/product/{product_id}")
    public ResponseEntity<String> addExistingProductToSupplier(
            @PathVariable("supplier_id") long supplierId,
            @PathVariable("product_id") long productId)   {
        try {
            supplierService.addExistingProductToSupplier(supplierId, productId);
            return ResponseEntity.ok("Product linked to supplier successfully");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{supplier_id}/product")
    public ResponseEntity<String> addProductToSupplier(
            @PathVariable("supplier_id") long supplierId,
            @RequestBody Product product){
        Supplier supplier = supplierService.findById(supplierId);

        if (supplier == null) {
            return ResponseEntity.notFound().build();
        }

        product.setSupplier(supplier);

        try {
            productService.save(product);
            return ResponseEntity.ok("Product added successfully");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
