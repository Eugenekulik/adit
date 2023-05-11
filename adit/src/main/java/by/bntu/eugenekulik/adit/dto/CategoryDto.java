package by.bntu.eugenekulik.adit.dto;

import by.bntu.eugenekulik.adit.entity.Category;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class CategoryDto {
    private Long categoryId;
    private String name;
    private Set<FeatureDto> features;

    public static CategoryDto fromCategory(Category category){
      if(category == null) return null;
      return CategoryDto.builder()
          .categoryId(category.getCategoryId())
          .features(getFeatures(category))
          .name(category.getName())
          .build();
    }

    private static Set<FeatureDto> getFeatures(Category category){
      Set<FeatureDto> features = category.getFeatures().stream()
          .map(FeatureDto::fromFeature)
          .collect(Collectors.toSet());
      if(category.getParent()!=null)
          features.addAll(getFeatures(category.getParent()));
      return features;
    }

  public Category toCategory() {
    Category category = new Category();
    category.setCategoryId(categoryId);
    category.setName(name);
    category.setFeatures(features.stream()
        .map(FeatureDto::toFeature)
        .collect(Collectors.toSet()));
    return category;
  }
}
