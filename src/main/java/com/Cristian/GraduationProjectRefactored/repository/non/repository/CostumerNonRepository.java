package com.Cristian.GraduationProjectRefactored.repository.non.repository;

import com.Cristian.GraduationProjectRefactored.entity.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CostumerNonRepository extends JpaRepository<Costumer, Long> {
    boolean existsByEmailOrPhone(String email , String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
