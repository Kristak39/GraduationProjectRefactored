package com.Cristian.GraduationProjectRefactored.repository;

import com.Cristian.GraduationProjectRefactored.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String name);
}
