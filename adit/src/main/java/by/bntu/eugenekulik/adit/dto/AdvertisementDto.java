package by.bntu.eugenekulik.adit.dto;

import by.bntu.eugenekulik.adit.domain.jpa.Address;
import by.bntu.eugenekulik.adit.domain.jpa.Advertisement;
import by.bntu.eugenekulik.adit.domain.jpa.Feature;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class AdvertisementDto {
  private long id;
  private String name;
  private String description;


  private Address address;

  private UserDto user;
  private LocalDateTime placedAt;
  private int status;
  private CategoryDto category;

  private List<ImageDto> images;

  private Double price;

  private List<FeatureDto> features;


  public Advertisement toAdvertisement() {
    Advertisement advertisement = new Advertisement();
    advertisement.setAdId(this.id);
    advertisement.setPlacedAt(this.placedAt);
    advertisement.setUser(this.user.toUser());
    advertisement.setStatus(this.status);
    advertisement.setAddress(this.address);
    advertisement.setCategory(category!=null?this.category.toCategory():null);
    advertisement.setName(this.name);
    advertisement.setDescription(this.description);
    advertisement.setPrice(this.price);
    advertisement.setImages(images.stream().map(ImageDto::toImage).toList());
    advertisement.setFeatures(features.stream()
        .collect(Collectors.toMap(FeatureDto::toFeature,FeatureDto::getValue)));
    return advertisement;
  }

  public static AdvertisementDto fromAdvertisement(Advertisement advertisement) {
    return AdvertisementDto.builder()
        .user(UserDto.fromUser(advertisement.getUser()))
        .id(advertisement.getAdId())
        .address(advertisement.getAddress())
        .category(CategoryDto.fromCategory(advertisement.getCategory()))
        .name(advertisement.getName())
        .description(advertisement.getDescription())
        .placedAt(advertisement.getPlacedAt())
        .status(advertisement.getStatus())
        .price(advertisement.getPrice())
        .images(advertisement.getImages()!=null?
            advertisement.getImages().stream().map(ImageDto::fromImage).toList(): Collections.emptyList())
        .features(advertisement.getFeatures().entrySet().stream()
            .map(FeatureDto::fromEntry).toList())
        .build();
  }
}