package by.bntu.eugenekulik.adit.controller;

import by.bntu.eugenekulik.adit.entity.Advertisement;
import by.bntu.eugenekulik.adit.entity.Image;
import by.bntu.eugenekulik.adit.service.AdvertisementService;
import by.bntu.eugenekulik.adit.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/images")
public class ImageController {


  @Autowired
  private ImageService imageService;
  @Autowired
  private AdvertisementService advertisementService;


  @PostMapping(value = "/add", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Map<String, String>> uploadAttachment(
      @RequestPart(value = "file") MultipartFile[] files, @RequestParam Long advertisementId) {
    Advertisement advertisement = advertisementService.getAdvertisement(advertisementId).get();
    Map<String, String> status = new HashMap<>();
    Arrays.stream(files).forEach(file -> {
      Image image = null;
      try {
        image = imageService.addImage(file,advertisement);
        status.put("status", "ok");
        status.put("imageId", image.getId().toString());
      } catch (IOException e) {
      }
    });
    return ResponseEntity.ok(status);
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<Resource> getImage(@PathVariable Long id, HttpServletRequest request) throws IOException {
    Image image = imageService.findImageById(id).get();
    Resource resource = imageService.loadFileAsResource(image.getFilename());
    String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    if (contentType == null) {
      contentType = "application/octet-stream";
    }
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "image; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }
}
