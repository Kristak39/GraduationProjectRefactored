package com.Cristian.GraduationProjectRefactored.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Product {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true, nullable = false)
    long productId;

    @Column(name = "product_name", nullable = false)
    String productName;

    @Column(name = "product_description", nullable = false)
    String productDescription;

    @Column(name = "product_price", nullable = false)
    double productPrice;

    @Column(name = "unit_in_stock", nullable = false)
    long unitInStock;

    @Column(name = "unit_on_order", nullable = false)
    long unitOnOrder;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne
    @JoinColumn(name = "costumer_id")
    Costumer costumer;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    Supplier supplier;
}
