package by.bntu.eugenekulik.adit.dao.jpa;

import by.bntu.eugenekulik.adit.domain.jpa.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

  Set<Category> findByParentOrderByNameAsc(Category category);

  Category findByName(String name);

  Page<Category> findAll(Pageable pageable);


  Page<Category> findByNameContainsOrderByNameAsc(String words, Pageable pageable);

}
