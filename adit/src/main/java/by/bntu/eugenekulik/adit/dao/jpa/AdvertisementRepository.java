package by.bntu.eugenekulik.adit.dao.jpa;

import by.bntu.eugenekulik.adit.domain.jpa.Advertisement;
import by.bntu.eugenekulik.adit.domain.jpa.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {

  Page<Advertisement> findAll(Pageable pageable);

  Page<Advertisement> findByStatus(Integer status, Pageable pageable);
  Page<Advertisement> findByNameContainsIgnoreCaseAndStatus(String name, Integer status,
                                                            Pageable pageable);

  Page<Advertisement> findByUser(User user, Pageable pageable);
  @Query("select a from Advertisement a where a.category.categoryId " +
      "in :categories and a.status = :status")
  Page<Advertisement> findByCategories(@Param("categories") long[] categories, Integer status, Pageable pageable);
}