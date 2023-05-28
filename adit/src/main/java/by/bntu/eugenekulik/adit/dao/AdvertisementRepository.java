package by.bntu.eugenekulik.adit.dao;

import by.bntu.eugenekulik.adit.entity.Advertisement;
import by.bntu.eugenekulik.adit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {

  Page<Advertisement> findAllByOrderByNameAsc(Pageable pageable);
  Page<Advertisement> findByNameContainsIgnoreCaseAndStatusOrderByNameAsc(String name, Integer status,
                                                                 Pageable pageable);

  Page<Advertisement> findByUserOrderByPlacedAt(User user, Pageable pageable);
  @Query("select a from Advertisement a where a.category.categoryId " +
      "in :categories and a.status = :status order by a.name asc")
  Page<Advertisement> findByCategories(@Param("categories") long[] categories, Integer status, Pageable pageable);
}