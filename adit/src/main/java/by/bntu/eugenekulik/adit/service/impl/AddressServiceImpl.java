package by.bntu.eugenekulik.adit.service.impl;

import by.bntu.eugenekulik.adit.dao.jpa.AddressRepository;
import by.bntu.eugenekulik.adit.domain.jpa.Address;
import by.bntu.eugenekulik.adit.service.AddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

  private AddressRepository addressRepository;


  @Override
  public Page<Address> search(Integer page, String words){
    return addressRepository.findByFields(words,PageRequest.of(page,10));
  }

  public AddressServiceImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }


  @Override
  public Address createAddress(Address address){
    return addressRepository.save(address);
  }


  @Override
  public Optional<Address> findById(Long id){
    return addressRepository.findById(id);
  }

  @Override
  public Page<Address> getPage(Integer page){
    return addressRepository.findAllByOrderByCountryAsc(PageRequest.of(page,10));
  }

  @Override
  public Address updateAddress(Address address){
    return addressRepository.save(address);
  }


  @Override
  public void deleteAddress(Long id){
    addressRepository.deleteById(id);
  }

}


