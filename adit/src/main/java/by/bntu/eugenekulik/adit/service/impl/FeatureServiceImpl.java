package by.bntu.eugenekulik.adit.service.impl;

import by.bntu.eugenekulik.adit.dao.jpa.FeatureRepository;
import by.bntu.eugenekulik.adit.domain.jpa.Feature;
import by.bntu.eugenekulik.adit.service.FeatureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureServiceImpl implements FeatureService {

  private final FeatureRepository featureRepository;

  public FeatureServiceImpl(FeatureRepository featureRepository) {
    this.featureRepository = featureRepository;
  }

  @Override
  public Optional<Feature> saveFeature(Feature feature){
    return Optional.ofNullable(featureRepository.save(feature));
  }

  @Override
  public void deleteFeature(Long id) {
    featureRepository.deleteById(id);
  }

  @Override
  public Optional<Feature> findFeature(Long id) {
    return featureRepository.findById(id);
  }

  @Override
  public Page<Feature> findPage(Integer page) {
    return featureRepository.findAllByOrderByNameAsc(PageRequest.of(page,10));
  }

  @Override
  public Feature updateFeature(Feature feature) {
    return featureRepository.save(feature);
  }

  @Override
  public Page<Feature> findByNameContains(String name, Integer page) {
    return featureRepository.findByNameContainsIgnoreCaseOrderByNameAsc(name, PageRequest.of(page,10));
  }
}
