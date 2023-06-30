package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.domain.jpa.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
  Page<User> getPage(int page);

  Optional<User> findUserById(Long id);

  User authorize(String username, String password);

  void deleteUser(Long id);

  User update(User user);

  Optional<User> create(User user);

  void addAdvertisementToFavourites(Long userId, Long advertisementId);

  void deleteAdvertisementFromFavourites(Long userId, Long advertisementId);
}
