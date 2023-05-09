package by.bntu.eugenekulik.adit.dao;

import by.bntu.eugenekulik.adit.entity.Advertisement;
import by.bntu.eugenekulik.adit.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {

  Page<Advertisement> findAll(Pageable pageable);
  Page<Advertisement> findByNameContainsIgnoreCase(String name,
                                                   Pageable pageable);
  List<Advertisement> findByCategory(Category category);
}