package com.Cristian.GraduationProjectRefactored.controller;

import com.Cristian.GraduationProjectRefactored.entity.Address;
import com.Cristian.GraduationProjectRefactored.entity.Costumer;
import com.Cristian.GraduationProjectRefactored.entity.Product;
import com.Cristian.GraduationProjectRefactored.entity.Supplier;
import com.Cristian.GraduationProjectRefactored.service.AddressService;
import com.Cristian.GraduationProjectRefactored.service.CostumerService;
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
@RequestMapping("/costumer")
public class CostumerController {

    @Autowired
    CostumerService costumerService;

    @Autowired
    AddressService addressService;
    @Autowired
    ProductService productService;

    @GetMapping("/get_all")
    public ResponseEntity<List<Costumer>> getAll(){
        try {
            List<Costumer> costumersList = costumerService.findAll();
            return new ResponseEntity<>(costumersList, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<Costumer> getById(@PathVariable long id){
        try{
            Costumer costumer = costumerService.findById(id);
            return new ResponseEntity<>(costumer, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCostumer(@PathVariable long id, @RequestBody Costumer costumer) {
        try {
            costumerService.update(costumer,id);
            return ResponseEntity.ok("Costumer updated successfully");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Costumer not found");
        }catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCostumer(@RequestBody Costumer costumer) {
        try {
            costumerService.save(costumer);
            return ResponseEntity.ok("Costumer created");
        }catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Costumer already exists");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCostumer(@PathVariable long id) {
        try {
            costumerService.deleteById(id);
            return ResponseEntity.ok("Costumer deleted");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Costumer not found");
        }
    }

    @PostMapping("/{customer_id}/addresses/{addressId}")
    public ResponseEntity<String> addExistingAddressToCustomer(
            @PathVariable("customer_id") long customerId,
            @PathVariable("addressId") long addressId) {
        try {
            costumerService.addExistingAddressToCustomer(customerId, addressId);
            return ResponseEntity.ok("Address linked to customer successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{customer_id}/addresses")
    public ResponseEntity<String> addAddressToCustomer(
            @PathVariable("customer_id") long customerId,
            @RequestBody Address address) {
        Costumer costumer = costumerService.findById(customerId);
        if (costumer == null) {
            return ResponseEntity.notFound().build();
        }

        address.setCostumer(costumer);

        try {
            addressService.save(address);
            return ResponseEntity.ok("Address added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{costumer_id}/product/{product_id}")
    public ResponseEntity<String> addExistingProductToCustomer(
            @PathVariable("costumer_id") long costumerId,
            @PathVariable("product_id") long productId)   {
        try {
            costumerService.addExistingProductToCostumer(costumerId, productId);
            return ResponseEntity.ok("Product linked to costumer successfully");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{costumer_id}/product")
    public ResponseEntity<String> addProductToCustomer(
            @PathVariable("costumer_id") long costumerId,
            @RequestBody Product product){
        Costumer costumer = costumerService.findById(costumerId);

        if (costumer == null) {
            return ResponseEntity.notFound().build();
        }

        product.setCostumer(costumer);

        try {
            productService.save(product);
            return ResponseEntity.ok("Product added successfully");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
