package by.bntu.eugenekulik.adit.controller;

import by.bntu.eugenekulik.adit.dto.CategoryDto;
import by.bntu.eugenekulik.adit.entity.Category;
import by.bntu.eugenekulik.adit.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

  private final CategoryService service;

  public CategoryController(CategoryService service) {
    this.service = service;
  }


  @PostMapping
  public ResponseEntity<Category> createCategory(@RequestParam String name, @RequestParam(required = false) String parentName) {
    Category category = new Category();
    category.setName(name);
    if (parentName != null) {
      Category parent = service.findCategoryByName(parentName);
      category.setParent(parent);
    }
    return new ResponseEntity<>(service.createCategory(category), HttpStatus.CREATED);
  }
  record CategoryFeature(Long categoryId, Long featureId){}
  @PatchMapping("/feature/add")
  public ResponseEntity<Category> addFeature(@RequestBody CategoryFeature temp) {
    return new ResponseEntity<>(service.addFeatureToCategory(temp.featureId, temp.categoryId), HttpStatus.OK);
  }


  @GetMapping
  public Iterable<CategoryDto> getPage(@RequestParam Integer page){
    Page<Category> result = service.getPage(page);
    return new PageImpl<>(result.stream()
        .map(CategoryDto::fromCategory)
        .toList(), PageRequest.of(page,10),result.getTotalElements());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDto> findCategory(@PathVariable Long id){
    return service.findById(id).map(CategoryDto::fromCategory)
        .map(ResponseEntity::ok)
        .orElse(new ResponseEntity<>(null,HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long id){
    return service.deleteCategory(id).map(CategoryDto::fromCategory)
        .map(ResponseEntity::ok)
        .orElse(new ResponseEntity<>(null,HttpStatus.NOT_FOUND));
  }

  @PatchMapping("/feature/delete")
  public ResponseEntity<Category> deleteFeature(@RequestBody CategoryFeature temp) {
    return new ResponseEntity<>(service.deleteFeature(temp.featureId, temp.categoryId), HttpStatus.OK);
  }
}
