package com.Cristian.GraduationProjectRefactored.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Category {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", unique = true, nullable = false)
    long categoryId;

    @Column(name = "category_name")
    String categoryName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy ="category" , cascade = CascadeType.ALL)
    @JsonIgnore
    List<Product> products;
}
