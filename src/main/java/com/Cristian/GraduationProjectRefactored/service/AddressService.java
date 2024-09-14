package com.Cristian.GraduationProjectRefactored.service;

import com.Cristian.GraduationProjectRefactored.entity.Address;
import com.Cristian.GraduationProjectRefactored.repository.AddressRepository;
import com.Cristian.GraduationProjectRefactored.repository.non.repository.AddressNonRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    AddressNonRepository addressNonRepository;

    boolean exists(Address address) {
        return  addressRepository.existsByStreetAndCityAndStateAndZipAndCountry(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip(),
                address.getCountry());
    }

    public void save(Address address) {
        if (exists(address)) {
            throw new EntityExistsException("Address already exists");
        }else {
            addressRepository.save(address);
        }
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    public void update(Address updateAddress , Long id) {
        Address existAddress = findById(id);

        if (updateAddress !=null) {
            if (updateAddress.getStreet()!=null
                    && !existAddress.getStreet().equals(updateAddress.getStreet())) {
                existAddress.setStreet(updateAddress.getStreet());
            }
            if (updateAddress.getCity()!=null
                    && !existAddress.getCity().equals(updateAddress.getCity())) {
                existAddress.setCity(updateAddress.getCity());
            }
            if (updateAddress.getState()!=null
                    && !existAddress.getState().equals(updateAddress.getState())) {
                existAddress.setState(updateAddress.getState());
            }
            if (updateAddress.getZip()!=null
                    && !existAddress.getZip().equals(updateAddress.getZip())) {
                existAddress.setZip(updateAddress.getZip());
            }
            if (updateAddress.getCountry()!=null
                    && !existAddress.getCountry().equals(updateAddress.getCountry())) {
                existAddress.setCountry(updateAddress.getCountry());
            }
        }

        addressRepository.save(existAddress);
    }

    public void deleteById(long id) {
        addressRepository.deleteById(id);
    }
}
