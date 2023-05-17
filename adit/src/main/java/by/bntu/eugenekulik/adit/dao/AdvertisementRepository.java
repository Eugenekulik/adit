package by.bntu.eugenekulik.adit.dao;

import by.bntu.eugenekulik.adit.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {

  Page<Advertisement> findAllByOrderByNameAsc(Pageable pageable);
  Page<Advertisement> findByNameContainsIgnoreCaseOrderByNameAsc(String name,
                                                                 Pageable pageable);

  @Query("select a from Advertisement a where a.category.categoryId in :categories order by a.name asc")
  Page<Advertisement> findByCategories(@Param("categories") long[] categories, Pageable pageable);
}