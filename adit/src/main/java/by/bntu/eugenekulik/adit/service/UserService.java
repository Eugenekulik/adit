package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.entity.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
  Page<User> getPage(int page);

  Optional<User> findUserById(Long id);

  User authorize(String username, String password);

  Optional<User> deleteUser(Long id);

  Optional<User> update(User user);

  Optional<User> create(User user);
}
