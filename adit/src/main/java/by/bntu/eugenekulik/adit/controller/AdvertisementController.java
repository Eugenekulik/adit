package by.bntu.eugenekulik.adit.controller;

import by.bntu.eugenekulik.adit.dto.AdvertisementDto;
import by.bntu.eugenekulik.adit.domain.jpa.Advertisement;
import by.bntu.eugenekulik.adit.domain.jpa.User;
import by.bntu.eugenekulik.adit.service.AdvertisementService;
import java.time.LocalDateTime;
import java.util.Collections;

import by.bntu.eugenekulik.adit.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/advertisement", produces = "application/json")
public class AdvertisementController {

  private final AdvertisementService advertisementService;

  private final UserService userService;

  public AdvertisementController(AdvertisementService advertisementService, UserService userService) {
    this.advertisementService = advertisementService;
    this.userService = userService;
  }

  @GetMapping
  public Iterable<AdvertisementDto> getAdvertisements(@RequestParam int page,
                                                      @RequestParam(required = false) String field,
                                                      @RequestParam(required = false) Boolean direction) {
    Page<Advertisement> result = advertisementService.getPage(page, field, direction);
    return new PageImpl<>(result.stream()
        .map(AdvertisementDto::fromAdvertisement)
        .toList(), PageRequest.of(page,10),result.getTotalElements());
  }

  @PostMapping
  public ResponseEntity<AdvertisementDto> createAdvertisement(@RequestBody Advertisement advertisement) {
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
  public ResponseEntity<AdvertisementDto> updateAdvertisement(@RequestBody AdvertisementDto advertisementDto) {
    return advertisementService.updateAdvertisement(advertisementDto.toAdvertisement())
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

  @GetMapping("/category")
  public Iterable<AdvertisementDto> getByCategory(@RequestParam Long categoryId,
                                                  @RequestParam(required = false) Integer page,
                                                  @RequestParam(required = false) String field,
                                                  @RequestParam(required = false) Boolean direction){
    if(page == null) page = 0;
    Page<Advertisement> result = advertisementService.getByCategory(categoryId,page, field, direction);
    return new PageImpl<>(result.stream()
        .map(AdvertisementDto::fromAdvertisement)
        .toList(), PageRequest.of(page,10),result.getTotalElements());
  }

  @GetMapping("/user/{id}")
  public Iterable<AdvertisementDto> getByUser(@PathVariable Long id, @RequestParam(required = false) Integer page,
                                              @RequestParam(required = false) String field,
                                              @RequestParam(required = false) Boolean direction){
    if(page == null) page = 0;
    Page<Advertisement> result = advertisementService.findByUser(id,page,field,direction);
    return new PageImpl<>(result.stream()
        .map(AdvertisementDto::fromAdvertisement)
        .toList(), PageRequest.of(page,10),result.getTotalElements());
  }

  @GetMapping("/favourites")
  public Iterable<AdvertisementDto> findFavourites(@RequestParam Long userId){
    User user = userService.findUserById(userId).orElseGet(null);
    return user!=null?user.getFavourites().stream()
        .map(AdvertisementDto::fromAdvertisement)
        .toList():Collections.emptyList();
  }


}
