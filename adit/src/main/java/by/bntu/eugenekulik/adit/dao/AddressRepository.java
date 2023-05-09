package by.bntu.eugenekulik.adit.dao;

import by.bntu.eugenekulik.adit.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface AddressRepository extends CrudRepository<Address, Long> {

  Page<Address> findAll(Pageable page);
}