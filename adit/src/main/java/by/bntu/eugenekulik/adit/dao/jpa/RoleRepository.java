package by.bntu.eugenekulik.adit.dao.jpa;

import by.bntu.eugenekulik.adit.domain.jpa.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
  Role findByName(String name);

  Page<Role> findAll(Pageable page);
}