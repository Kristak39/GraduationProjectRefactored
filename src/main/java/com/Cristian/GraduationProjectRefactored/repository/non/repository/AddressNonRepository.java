package com.Cristian.GraduationProjectRefactored.repository.non.repository;

import com.Cristian.GraduationProjectRefactored.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AddressNonRepository extends JpaRepository<Address, Long> {
    boolean existsByStreetAndCityAndStateAndZipAndCountry(String street, String city, String state, String zip, String country);
}
