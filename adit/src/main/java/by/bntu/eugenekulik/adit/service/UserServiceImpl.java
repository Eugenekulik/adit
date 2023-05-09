package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.dao.RoleRepository;
import by.bntu.eugenekulik.adit.dao.UserRepository;
import by.bntu.eugenekulik.adit.entity.Role;
import by.bntu.eugenekulik.adit.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private PasswordEncoder encoder;
  @Autowired
  UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Page<User> getPage(int page) {
    return userRepository.findAll(PageRequest.of(page, 10));
  }

  @Override
  public Optional<User> findUserById(Long id){
    return userRepository.findById(id);
  }

  @Override
  public User authorize(String username, String password) {
    User current = userRepository.findByLogin(username);
    if(current != null){
      if(encoder.matches(password,current.getPassword())) {
        return current;
      }
      else throw new CredentialsExpiredException("bad password");
    }
    throw new UsernameNotFoundException("user with name " + username + " not found");
  }

  @Override
  public Optional<User> deleteUser(Long id) {
    User user = userRepository.deleteUserByUserId(id);
    return Optional.ofNullable(user);
  }

  @Override
  public Optional<User> update(User user) {
    user = userRepository.save(user);
    return Optional.ofNullable(user);
  }

  @Override
  public  Optional<User> create(User user){
    user.setPassword(encoder.encode(user.getPassword()));
    Role role = roleRepository.findByName("client");
    user.getRoles().add(role);
    user = userRepository.save(user);
    return Optional.ofNullable(user);
  }
}
