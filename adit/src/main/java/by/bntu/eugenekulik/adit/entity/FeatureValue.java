package by.bntu.eugenekulik.adit.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class FeatureValue {
  private Long featureId;

  private String value;
}
