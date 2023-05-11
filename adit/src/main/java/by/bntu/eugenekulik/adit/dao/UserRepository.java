package by.bntu.eugenekulik.adit.dao;

import by.bntu.eugenekulik.adit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    User deleteUserByUserId(Long id);
    User findByLogin(String login);
}