package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.dao.CategoryRepository;
import by.bntu.eugenekulik.adit.dao.FeatureRepository;
import by.bntu.eugenekulik.adit.entity.Category;
import by.bntu.eugenekulik.adit.entity.Feature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  private final FeatureRepository featureRepository;

  public CategoryService(CategoryRepository categoryRepository, FeatureRepository featureRepository) {
    this.categoryRepository = categoryRepository;
    this.featureRepository = featureRepository;
  }

  public Category addFeatureToCategory(Long featureId, long categoryId){
    Feature feature = featureRepository.findById(featureId)
        .orElseThrow(()-> new IllegalArgumentException("not found feature with id: " + featureId));
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(()-> new IllegalArgumentException("not found category with id: " + categoryId));
    category.getFeatures().add(feature);
    return categoryRepository.save(category);
  }

  public Category findCategoryByName(String parent) {
    return categoryRepository.findByName(parent);
  }

  public Category createCategory(Category category) {
    return categoryRepository.save(category);
  }

  public Page<Category> getPage(Integer page) {
    return categoryRepository.findAll(PageRequest.of(page, 10));
  }

  public Optional<Category> findById(Long id) {
    return categoryRepository.findById(id);
  }

  public Optional<Category> deleteCategory(Long id) {
    return categoryRepository.deleteByCategoryId(id);
  }

  public Category deleteFeature(Long featureId, Long categoryId) {
    Category category = categoryRepository.findById(categoryId).get();
    category.getFeatures().removeIf(feature -> feature.getFeatureId().equals(featureId));
    categoryRepository.save(category);
    return category;
  }
}
