package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.domain.jpa.Role;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RoleService {
  Role createRole(Role role);

  Role updateRole(Role role);

  void deleteRole(Long id);

  Page<Role> getRoles(Integer page);

  Optional<Role> findById(Long id);
}
