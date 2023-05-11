package by.bntu.eugenekulik.adit.dao;

import by.bntu.eugenekulik.adit.entity.Advertisement;
import by.bntu.eugenekulik.adit.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {

  Page<Advertisement> findAll(Pageable pageable);
  Page<Advertisement> findByNameContainsIgnoreCase(String name,
                                                   Pageable pageable);

  @Query("select a from Advertisement a where a.category.categoryId in :categories")
  Page<Advertisement> findByCategories(@Param("categories") long[] categories, Pageable pageable);
}