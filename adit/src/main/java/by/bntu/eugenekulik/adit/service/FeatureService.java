package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.domain.jpa.Feature;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface FeatureService {
  Optional<Feature> saveFeature(Feature feature);

  void deleteFeature(Long id);

  Optional<Feature> findFeature(Long id);

  Page<Feature> findPage(Integer page);

  Feature updateFeature(Feature feature);

  Page<Feature> findByNameContains(String name, Integer page);
}
