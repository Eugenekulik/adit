package by.bntu.eugenekulik.adit.controller;

import by.bntu.eugenekulik.adit.entity.Role;
import by.bntu.eugenekulik.adit.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/role", produces = "application/json")
public class RoleController {

  private RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }


  @GetMapping("/{id}")
  public ResponseEntity<Role> getRole(@PathVariable Long id){
    return roleService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
  }

  @GetMapping
  public Iterable<Role> getRoles(@RequestParam Integer page){
    return roleService.getRoles(page);
  }


  @PostMapping
  public Role createRole(@RequestParam Role role){
    return roleService.createRole(role);
  }


  @DeleteMapping("/{id}")
  public Long deleteRole(@PathVariable Long id){
    roleService.deleteRole(id);
    return id;
  }


  @PatchMapping
  public Role updateRole(@RequestParam Role role){
    return roleService.updateRole(role);
  }

}