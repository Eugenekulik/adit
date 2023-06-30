package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.domain.jpa.Category;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

public interface CategoryService {
  Category addFeatureToCategory(Long featureId, long categoryId);

  Category findCategoryByName(String name);

  Page<Category> searchByName(String words, Integer page);

  Category createCategory(Category category);

  Page<Category> getPage(Integer page, String field, Boolean direction);

  Optional<Category> findById(Long id);

  void deleteCategory(Long id);

  Category deleteFeature(Long featureId, Long categoryId);

  Set<Category> findChildren(Long parentId);

  Category updateCategory(Category category);
}
