package com.Cristian.GraduationProjectRefactored.controller;

import com.Cristian.GraduationProjectRefactored.entity.Address;
import com.Cristian.GraduationProjectRefactored.entity.Category;
import com.Cristian.GraduationProjectRefactored.service.AddressService;
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
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/get_all")
    public ResponseEntity<List<Address>> getAll(){
        try {
            List<Address> addressesList = addressService.findAll();
            return new ResponseEntity<>(addressesList, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("get_by_id/{id}")
    public ResponseEntity<Address> getById(@PathVariable long id){
        try {
            Address address = addressService.findById(id);
            return new ResponseEntity<>(address, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAddress(@PathVariable long id, @RequestBody Address address) {
        try {
            addressService.update(address,id);
            return ResponseEntity.ok("Address updated successfully");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
        }catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAddress(@RequestBody Address address) {
        try {
            addressService.save(address);
            return ResponseEntity.ok("Address created");
        }catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Address already exists");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        try {
            addressService.deleteById(id);
            return ResponseEntity.ok("Address deleted");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
        }
    }
}
