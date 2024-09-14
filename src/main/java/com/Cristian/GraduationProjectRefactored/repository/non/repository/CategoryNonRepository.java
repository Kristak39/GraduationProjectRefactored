package com.Cristian.GraduationProjectRefactored.repository.non.repository;

import com.Cristian.GraduationProjectRefactored.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CategoryNonRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
