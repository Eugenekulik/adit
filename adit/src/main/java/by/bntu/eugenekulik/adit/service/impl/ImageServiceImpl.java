package by.bntu.eugenekulik.adit.service.impl;

import by.bntu.eugenekulik.adit.dao.jpa.ImageRepository;
import by.bntu.eugenekulik.adit.domain.jpa.Advertisement;
import by.bntu.eugenekulik.adit.domain.jpa.Image;
import by.bntu.eugenekulik.adit.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {


  private ImageRepository imageRepository;
  private ResourceLoader resourceLoader;
  @Value("${image.path}")
  private String path;

  private HttpServletRequest request;

  public ImageServiceImpl(ImageRepository imageRepository, ResourceLoader resourceLoader, HttpServletRequest request) {
    this.imageRepository = imageRepository;
    this.resourceLoader = resourceLoader;
    this.request = request;
  }

  @Override
  public Image addImage(MultipartFile file, Advertisement advertisement) throws IOException {
    String curDate = LocalDateTime.now().getNano() + "";
    String fileName = "image_" + curDate + "_" + file.getOriginalFilename()
        .toLowerCase().replaceAll(" ", "-");
    Resource folderResource = resourceLoader.getResource("classpath:images/");
    File folder = folderResource.getFile();
    if(!folder.exists()) folder.mkdirs();
    File targetFile = new File(folder, fileName);
    FileCopyUtils.copy(file.getBytes(), targetFile);

    String extension = fileName.substring(fileName.lastIndexOf("."));
    Image image = new Image();
    image.setFilename(fileName);
    image.setAdvertisement(advertisement);
    image.setExtension(extension);
    imageRepository.save(image);
    return image;
  }


  @Override
  public Optional<Image> findImageById(Long id){
    return imageRepository.findById(id);
  }

  @Override
  public Resource loadFileAsResource(String fileName) {
    return resourceLoader.getResource("classpath:images/" + fileName);
  }

  @Override
  public void deleteImage(Long id) {
    imageRepository.deleteById(id);
  }
}
