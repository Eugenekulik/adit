package by.bntu.eugenekulik.adit.service.impl;

import by.bntu.eugenekulik.adit.dao.jpa.RoleRepository;
import by.bntu.eugenekulik.adit.domain.jpa.Role;
import by.bntu.eugenekulik.adit.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

  private RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role createRole(Role role){
    return roleRepository.save(role);
  }


  @Override
  public Role updateRole(Role role){
    return roleRepository.save(role);
  }

  @Override
  public void deleteRole(Long id){
    roleRepository.deleteById(id);
  }

  @Override
  public Page<Role> getRoles(Integer page){
    return roleRepository.findAll(PageRequest.of(page,10));
  }

  @Override
  public Optional<Role> findById(Long id){
    return roleRepository.findById(id);
  }

}
