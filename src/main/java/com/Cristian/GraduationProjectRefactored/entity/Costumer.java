package com.Cristian.GraduationProjectRefactored.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Costumer {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "costumer_id", unique = true, nullable = false)
    long costumerId;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "phone", nullable = false, unique = true)
    String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "costumer", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Address> addresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "costumer", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Product> products;
}
