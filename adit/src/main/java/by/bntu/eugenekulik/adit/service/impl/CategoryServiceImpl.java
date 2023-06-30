package by.bntu.eugenekulik.adit.service.impl;

import by.bntu.eugenekulik.adit.dao.jpa.CategoryRepository;
import by.bntu.eugenekulik.adit.dao.jpa.FeatureRepository;
import by.bntu.eugenekulik.adit.domain.jpa.Category;
import by.bntu.eugenekulik.adit.domain.jpa.Feature;
import by.bntu.eugenekulik.adit.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  private final FeatureRepository featureRepository;

  public CategoryServiceImpl(CategoryRepository categoryRepository, FeatureRepository featureRepository) {
    this.categoryRepository = categoryRepository;
    this.featureRepository = featureRepository;
  }

  @Override
  public Category addFeatureToCategory(Long featureId, long categoryId){
    Feature feature = featureRepository.findById(featureId)
        .orElseThrow(()-> new IllegalArgumentException("not found feature with id: " + featureId));
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(()-> new IllegalArgumentException("not found category with id: " + categoryId));
    category.getFeatures().add(feature);
    return categoryRepository.save(category);
  }

  @Override
  public Category findCategoryByName(String name) {
    return categoryRepository.findByName(name);
  }

  @Override
  public Page<Category> searchByName(String words, Integer page){
    return categoryRepository.findByNameContainsOrderByNameAsc(words, PageRequest.of(page, 10));
  }

  @Override
  public Category createCategory(Category category) {
    return categoryRepository.save(category);
  }

  @Override
  public Page<Category> getPage(Integer page, String field, Boolean direction) {
    if(field==null)field = "name";
    if(direction == null)direction = true;
    return categoryRepository.findAll(PageRequest.of(page, 10,
        Sort.by(direction? Sort.Direction.ASC:Sort.Direction.DESC, field)));
  }

  @Override
  public Optional<Category> findById(Long id) {
    return categoryRepository.findById(id);
  }

  @Override
  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }

  @Override
  public Category deleteFeature(Long featureId, Long categoryId) {
    Category category = categoryRepository.findById(categoryId).get();
    category.getFeatures().removeIf(feature -> feature.getFeatureId().equals(featureId));
    categoryRepository.save(category);
    return category;
  }

  @Override
  public Set<Category> findChildren(Long parentId) {
    Set<Category> children = new HashSet<>();
    if(parentId != null) {
      categoryRepository.findById(parentId).ifPresent(
          category -> children.addAll(categoryRepository.findByParentOrderByNameAsc(category)));
    } else children.addAll(categoryRepository.findByParentOrderByNameAsc(null));
    return children;
  }

  @Override
  public Category updateCategory(Category category) {
    if(category.getCategoryId()==null) throw new RuntimeException("categoryId could not be null");
    return categoryRepository.save(category);
  }
}
