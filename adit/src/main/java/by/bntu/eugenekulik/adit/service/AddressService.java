package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.domain.jpa.Address;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface AddressService {
  Page<Address> search(Integer page, String words);

  Address createAddress(Address address);

  Optional<Address> findById(Long id);

  Page<Address> getPage(Integer page);

  Address updateAddress(Address address);

  void deleteAddress(Long id);
}
