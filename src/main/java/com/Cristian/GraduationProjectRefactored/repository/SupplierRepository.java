package com.Cristian.GraduationProjectRefactored.repository;

import com.Cristian.GraduationProjectRefactored.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByEmailOrPhone(String email, String phone);
}
