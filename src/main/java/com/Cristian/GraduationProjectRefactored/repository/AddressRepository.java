package com.Cristian.GraduationProjectRefactored.repository;

import com.Cristian.GraduationProjectRefactored.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsByStreetAndCityAndStateAndZipAndCountry(String street, String city, String state, String zip, String country);
}
