package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.domain.jpa.Advertisement;
import by.bntu.eugenekulik.adit.domain.jpa.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageService {
  Image addImage(MultipartFile file, Advertisement advertisement) throws IOException;

  Optional<Image> findImageById(Long id);

  Resource loadFileAsResource(String fileName);

  void deleteImage(Long id);
}
