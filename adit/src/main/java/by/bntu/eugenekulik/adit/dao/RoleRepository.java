package by.bntu.eugenekulik.adit.dao;

import by.bntu.eugenekulik.adit.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {
  Role findByName(String name);

  Page<Role> findAll(Pageable page);
}