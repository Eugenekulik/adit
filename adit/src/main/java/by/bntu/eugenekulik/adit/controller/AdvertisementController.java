package by.bntu.eugenekulik.adit.controller;

import by.bntu.eugenekulik.adit.dto.AdvertisementDto;
import by.bntu.eugenekulik.adit.entity.Advertisement;
import by.bntu.eugenekulik.adit.service.AdvertisementService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/advertisement", produces = "application/json")
public class AdvertisementController {

  @Autowired
  private AdvertisementService advertisementService;

  @GetMapping
  public Iterable<AdvertisementDto> getAdvertisements(@RequestParam int page) {
    Page<Advertisement> result = advertisementService.getPage(page);
    return new PageImpl<>(result.stream()
        .map(AdvertisementDto::fromAdvertisement)
        .toList(), PageRequest.of(page,10),result.getTotalElements());
  }

  @PostMapping
  public ResponseEntity<AdvertisementDto> createAdvertisement(@RequestParam Advertisement advertisement) {
    advertisement.setPlacedAt(LocalDateTime.now());
    return new ResponseEntity<>(
        AdvertisementDto.fromAdvertisement(advertisementService.save(advertisement)),
        HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AdvertisementDto> getAdvertisement(@PathVariable Long id) {
    return advertisementService.getAdvertisement(id)
        .map(AdvertisementDto::fromAdvertisement)
        .map(ResponseEntity::ok)
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PatchMapping
  public ResponseEntity<AdvertisementDto> updateAdvertisement(@RequestParam Advertisement advertisement) {
    return advertisementService.updateAdvertisement(advertisement)
        .map(AdvertisementDto::fromAdvertisement)
        .map(ResponseEntity::ok)
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<AdvertisementDto> deleteAdvertisement(@PathVariable Long id) {
    return advertisementService.changeStatus(id, 3)
        .map(AdvertisementDto::fromAdvertisement)
        .map(ResponseEntity::ok)
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}