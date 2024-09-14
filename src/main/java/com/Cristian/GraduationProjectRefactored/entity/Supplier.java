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
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id", unique = true, nullable = false)
    long supplierId;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "phone", nullable = false, unique = true)
    String phone;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Address> addresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supplier", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Product> products;
}
