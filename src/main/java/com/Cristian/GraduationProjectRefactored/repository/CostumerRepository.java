package com.Cristian.GraduationProjectRefactored.repository;

import com.Cristian.GraduationProjectRefactored.entity.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumerRepository extends JpaRepository<Costumer, Long> {
    boolean existsByEmailOrPhone(String email , String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
