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
public class Address {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", unique = true, nullable = false)
    long addressId;

    @Column(name = "street",nullable = false)
    String street;

    @Column(name = "city",nullable = false)
    String city;

    @Column(name = "state",nullable = false)
    String state;

    @Column(name = "zip",nullable = false)
    String zip;

    @Column(name = "country",nullable = false)
    String country;

    @ManyToOne
    @JoinColumn(name = "costumer_id")
    Costumer costumer;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    Supplier supplier;
}
