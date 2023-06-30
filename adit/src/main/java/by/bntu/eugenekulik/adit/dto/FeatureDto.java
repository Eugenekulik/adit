package by.bntu.eugenekulik.adit.dto;

import by.bntu.eugenekulik.adit.domain.jpa.Feature;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

@Data
@Builder
public class FeatureDto {

  private Long featureId;
  private String name;
  private String description;
  private String value;


  public static FeatureDto fromFeature(Feature feature){
    return FeatureDto.builder()
        .featureId(feature.getFeatureId())
        .name(feature.getName())
        .description(feature.getDescription())
        .build();
  }

  public static FeatureDto fromEntry(Map.Entry<Feature,String> entry){
    return FeatureDto.builder()
        .featureId(entry.getKey().getFeatureId())
        .name(entry.getKey().getName())
        .description(entry.getKey().getDescription())
        .value(entry.getValue())
        .build();
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FeatureDto that = (FeatureDto) o;
    return Objects.equals(featureId, that.featureId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(featureId);
  }

  public Feature toFeature() {
    Feature feature = new Feature();
    feature.setFeatureId(featureId);
    feature.setName(name);
    feature.setDescription(description);
    return feature;
  }
}
