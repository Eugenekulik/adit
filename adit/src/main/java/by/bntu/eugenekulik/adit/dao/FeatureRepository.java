package by.bntu.eugenekulik.adit.dao;

import by.bntu.eugenekulik.adit.entity.Feature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface FeatureRepository extends CrudRepository<Feature, Long> {
  Page<Feature> findAll(Pageable page);

  Page<Feature> findByNameContainsIgnoreCase(String name, Pageable pageable);
}
