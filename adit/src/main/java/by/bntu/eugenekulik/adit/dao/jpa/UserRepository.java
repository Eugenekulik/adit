package by.bntu.eugenekulik.adit.dao.jpa;

import by.bntu.eugenekulik.adit.domain.jpa.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByEmail(String email);
    Page<User> findAllByOrderByLoginAsc(Pageable pageable);

    User findByLogin(String login);
}