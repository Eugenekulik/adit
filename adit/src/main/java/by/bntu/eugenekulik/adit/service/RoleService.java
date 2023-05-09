package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.dao.RoleRepository;
import by.bntu.eugenekulik.adit.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

  private RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public Role createRole(Role role){
    return roleRepository.save(role);
  }


  public Role updateRole(Role role){
    return roleRepository.save(role);
  }

  public void deleteRole(Long id){
    roleRepository.deleteById(id);
  }

  public Page<Role> getRoles(Integer page){
    return roleRepository.findAll(PageRequest.of(page,10));
  }

  public Optional<Role> findById(Long id){
    return roleRepository.findById(id);
  }

}
