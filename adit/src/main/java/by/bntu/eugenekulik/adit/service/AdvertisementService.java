package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.dto.AdvertisementDto;
import by.bntu.eugenekulik.adit.dto.CategoryDto;
import by.bntu.eugenekulik.adit.entity.Advertisement;
import by.bntu.eugenekulik.adit.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AdvertisementService {
  Page<Advertisement> getPage(int page);

  Advertisement save(Advertisement advertisement);

  Optional<Advertisement> getAdvertisement(Long id);

  @Transactional
  Optional<Advertisement> updateAdvertisement(Advertisement advertisement);

  Optional<Advertisement> changeStatus(Long id, int status);

  default boolean validate(int status) {
    return status < 4 && status > 0;
  }

  Page<Advertisement> search(String words, Integer page);

  Page<Advertisement> getByCategory(Long categoryId, Integer page);
}
