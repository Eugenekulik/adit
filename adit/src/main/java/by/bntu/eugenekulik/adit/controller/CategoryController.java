package by.bntu.eugenekulik.adit.controller;

import by.bntu.eugenekulik.adit.dto.CategoryDto;
import by.bntu.eugenekulik.adit.entity.Category;
import by.bntu.eugenekulik.adit.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

  private final CategoryService service;

  public CategoryController(CategoryService service) {
    this.service = service;
  }


  @GetMapping("/children")
  public Iterable<Category> getChildren(@RequestParam(required = false) Long parentId){
    return service.findChildren(parentId);
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


  @PatchMapping
  public ResponseEntity<Category> updateCategory(@RequestBody Category category){
    return ResponseEntity
        .ok(service.updateCategory(category));
  }


  @GetMapping("/page")
  public Iterable<Category> getPage(@RequestParam Integer page){
    return service.getPage(page);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Category> findCategory(@PathVariable Long id){
    return service.findById(id)
        .map(ResponseEntity::ok)
        .orElse(new ResponseEntity<>(null,HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Category> deleteCategory(@PathVariable Long id){
    return service.deleteCategory(id)
        .map(ResponseEntity::ok)
        .orElse(new ResponseEntity<>(null,HttpStatus.NOT_FOUND));
  }

  @PatchMapping("/feature/delete")
  public ResponseEntity<Category> deleteFeature(@RequestBody CategoryFeature temp) {
    return new ResponseEntity<>(service.deleteFeature(temp.featureId, temp.categoryId), HttpStatus.OK);
  }

  @GetMapping("/search")
  public Iterable<Category> findByName(@RequestParam String words, @RequestParam(required = false) Integer page){
    page = page==null?0:page;
    return service.searchByName(words,page);

  }
}
