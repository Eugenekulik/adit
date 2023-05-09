package by.bntu.eugenekulik.adit.controller;

import by.bntu.eugenekulik.adit.dto.AdvertisementDto;
import by.bntu.eugenekulik.adit.entity.Advertisement;
import by.bntu.eugenekulik.adit.service.AdvertisementService;
import by.bntu.eugenekulik.adit.service.ImageService;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/search", produces = "application/json")
public class SearchController {

  private AdvertisementService advertisementService;

  public SearchController(AdvertisementService advertisementService) {
    this.advertisementService = advertisementService;
  }


  @GetMapping
  public Iterable<AdvertisementDto> find(@RequestParam String words, @RequestParam Integer page){
    Page<Advertisement> result =  advertisementService.search(words, page);
    return new PageImpl<>(result.stream()
        .map(AdvertisementDto::fromAdvertisement)
        .toList(), PageRequest.of(page,10),result.getTotalElements());
  }

}
