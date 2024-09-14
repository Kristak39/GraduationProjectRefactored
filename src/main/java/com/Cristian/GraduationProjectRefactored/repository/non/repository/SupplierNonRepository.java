package com.Cristian.GraduationProjectRefactored.repository.non.repository;

import com.Cristian.GraduationProjectRefactored.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SupplierNonRepository extends JpaRepository<Supplier,Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByEmailOrPhone(String email, String phone);
}
