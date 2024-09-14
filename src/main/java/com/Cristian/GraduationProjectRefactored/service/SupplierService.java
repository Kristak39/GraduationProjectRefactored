package com.Cristian.GraduationProjectRefactored.service;

import com.Cristian.GraduationProjectRefactored.entity.Address;
import com.Cristian.GraduationProjectRefactored.entity.Costumer;
import com.Cristian.GraduationProjectRefactored.entity.Product;
import com.Cristian.GraduationProjectRefactored.entity.Supplier;
import com.Cristian.GraduationProjectRefactored.repository.AddressRepository;
import com.Cristian.GraduationProjectRefactored.repository.ProductRepository;
import com.Cristian.GraduationProjectRefactored.repository.SupplierRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ProductRepository productRepository;

    boolean exists(Supplier supplier) {
       return supplierRepository.existsByEmailOrPhone(supplier.getEmail(), supplier.getPhone());
    }

    public void save(Supplier supplier) {
        if (exists(supplier)) {
            throw new EntityExistsException("Supplier already exists");
        }else {
            supplierRepository.save(supplier);
        }
    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Supplier findById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
    }

    public void update(Supplier updateSupplier, long supplierId) {
        Supplier existingSupplier = findById(supplierId);

        if(updateSupplier != null) {
            if (updateSupplier.getFirstName() != null
                    && existingSupplier.getFirstName().equals(updateSupplier.getFirstName())) {
                existingSupplier.setFirstName(updateSupplier.getFirstName());
            }

            if (updateSupplier.getLastName() != null
                    && existingSupplier.getLastName().equals(updateSupplier.getLastName())) {
                existingSupplier.setLastName(updateSupplier.getLastName());
            }

            if (updateSupplier.getPhone() != null
                    && !existingSupplier.getPhone().equals(updateSupplier.getPhone())) {
                if (supplierRepository.existsByPhone(updateSupplier.getPhone())) {
                    throw new EntityExistsException("Phone already exists");
                } else {
                    existingSupplier.setPhone(updateSupplier.getPhone());
                }
            }

            if (updateSupplier.getEmail() != null
                    && !existingSupplier.getEmail().equals(updateSupplier.getEmail())) {
                if (supplierRepository.existsByEmail(updateSupplier.getEmail())) {
                    throw new EntityExistsException("Email already exists");
                } else {
                    existingSupplier.setEmail(updateSupplier.getEmail());
                }
            }
        }

        supplierRepository.save(existingSupplier);
    }

    public void deleteById(Long id) {
        supplierRepository.deleteById(id);
    }

    public void addExistingAddressToSupplier(long supplierId, long addressId) {
        Supplier supplier= supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        if (!supplier.getAddresses().contains(address)) {
            supplier.getAddresses().add(address);
            supplierRepository.save(supplier);
        }
    }

    public void addExistingProductToSupplier(long supplierId, long productId) {
        Supplier supplier= supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        if (!supplier.getProducts().contains(product)) {
            supplier.getProducts().add(product);
            supplierRepository.save(supplier);
        }
    }

}
