package by.bntu.eugenekulik.adit.dao.jpa;

import by.bntu.eugenekulik.adit.domain.jpa.Feature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface FeatureRepository extends CrudRepository<Feature, Long> {
  Page<Feature> findAllByOrderByNameAsc(Pageable page);

  Page<Feature> findByNameContainsIgnoreCaseOrderByNameAsc(String name, Pageable pageable);
}
