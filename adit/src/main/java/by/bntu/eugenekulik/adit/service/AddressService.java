package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.dao.AddressRepository;
import by.bntu.eugenekulik.adit.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

  private AddressRepository addressRepository;


  public AddressService(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }


  public Address createAddress(Address address){
    return addressRepository.save(address);
  }


  public Optional<Address> findById(Long id){
    return addressRepository.findById(id);
  }

  public Page<Address> getPage(Integer page){
    return addressRepository.findAll(PageRequest.of(page,10));
  }

  public Address updateAddress(Address address){
    return addressRepository.save(address);
  }


  public void deleteAddress(Long id){
    addressRepository.deleteById(id);
  }

}


