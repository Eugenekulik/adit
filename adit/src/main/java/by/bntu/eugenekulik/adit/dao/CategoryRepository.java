package by.bntu.eugenekulik.adit.dao;

import by.bntu.eugenekulik.adit.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

  Set<Category> findByParent(Category category);

  Category findByName(String name);

  Page<Category> findAll(Pageable page);

  Optional<Category> deleteByCategoryId(Long id);

  Page<Category> findByNameContains(String words, Pageable pageable);

}
