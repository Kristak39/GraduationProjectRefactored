package com.Cristian.GraduationProjectRefactored.service;

import com.Cristian.GraduationProjectRefactored.entity.Address;
import com.Cristian.GraduationProjectRefactored.entity.Costumer;
import com.Cristian.GraduationProjectRefactored.entity.Product;
import com.Cristian.GraduationProjectRefactored.entity.Supplier;
import com.Cristian.GraduationProjectRefactored.repository.AddressRepository;
import com.Cristian.GraduationProjectRefactored.repository.CostumerRepository;
import com.Cristian.GraduationProjectRefactored.repository.ProductRepository;
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
public class CostumerService {

    @Autowired
    CostumerRepository costumerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ProductRepository productRepository;

    boolean exists(Costumer costumer) {
        return costumerRepository.existsByEmailOrPhone(costumer.getEmail(), costumer.getPhone());
    }

    public void save(Costumer costumer) {
        if (exists(costumer)) {
            throw new EntityExistsException("Costumer already exists");
        }else {
            costumerRepository.save(costumer);
        }
    }

    public List<Costumer> findAll() {
        return costumerRepository.findAll();
    }

    public Costumer findById(Long id) {
        return costumerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Costumer not found"));
    }

    public void update(Costumer updateCostumer, Long id ) {
        Costumer existingCostumer = findById(id);

        if (updateCostumer != null) {
            if (updateCostumer.getFirstName() != null
                    && !existingCostumer.getFirstName().equals(updateCostumer.getFirstName())) {
                existingCostumer.setFirstName(updateCostumer.getFirstName());
            }
            if (updateCostumer.getLastName() != null
                    && !existingCostumer.getLastName().equals(updateCostumer.getLastName())) {
                existingCostumer.setLastName(updateCostumer.getLastName());
            }
            if (updateCostumer.getPhone() != null
                    && !existingCostumer.getPhone().equals(updateCostumer.getPhone())) {
                if (costumerRepository.existsByPhone(updateCostumer.getPhone())) {
                    throw new EntityExistsException("Phone already exists");
                } else {
                    existingCostumer.setPhone(updateCostumer.getPhone());
                }
            }
            if (updateCostumer.getEmail() != null
                    && !existingCostumer.getEmail().equals(updateCostumer.getEmail())) {
                if (costumerRepository.existsByEmail(updateCostumer.getEmail())) {
                    throw new EntityExistsException("Email already exists");
                } else {
                    existingCostumer.setEmail(updateCostumer.getEmail());
                }
            }
        }

        costumerRepository.save(existingCostumer);
    }

    public void deleteById(Long id) {
        costumerRepository.deleteById(id);
    }

    public void addExistingAddressToCustomer(long customerId, long addressId) {
        Costumer costumer = costumerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        if (!costumer.getAddresses().contains(address)) {
            costumer.getAddresses().add(address);
            costumerRepository.save(costumer);
        }
    }

    public void addExistingProductToCostumer(long costumerId, long productId) {
        Costumer costumer= costumerRepository.findById(costumerId)
                .orElseThrow(() -> new IllegalArgumentException("Costumer not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        if (!costumer.getProducts().contains(product)) {
            costumer.getProducts().add(product);
            costumerRepository.save(costumer);
        }
    }
}
