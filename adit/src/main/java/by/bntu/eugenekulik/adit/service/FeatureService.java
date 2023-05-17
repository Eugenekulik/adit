package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.dao.FeatureRepository;
import by.bntu.eugenekulik.adit.entity.Feature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureService {

  private final FeatureRepository featureRepository;

  public FeatureService(FeatureRepository featureRepository) {
    this.featureRepository = featureRepository;
  }

  public Optional<Feature> saveFeature(Feature feature){
    return Optional.ofNullable(featureRepository.save(feature));
  }

  public void deleteFeature(Long id) {
    featureRepository.deleteById(id);
  }

  public Optional<Feature> findFeature(Long id) {
    return featureRepository.findById(id);
  }

  public Page<Feature> findPage(Integer page) {
    return featureRepository.findAllByOrderByNameAsc(PageRequest.of(page,10));
  }

  public Feature updateFeature(Feature feature) {
    return featureRepository.save(feature);
  }

  public Page<Feature> findByNameContains(String name, Integer page) {
    return featureRepository.findByNameContainsIgnoreCaseOrderByNameAsc(name, PageRequest.of(page,10));
  }
}
