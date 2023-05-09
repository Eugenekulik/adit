package by.bntu.eugenekulik.adit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Setter
@Getter
@RequiredArgsConstructor
@ToString
public class Feature {
  @Id
  private Long featureId;

  private String name;

  private String description;

}
