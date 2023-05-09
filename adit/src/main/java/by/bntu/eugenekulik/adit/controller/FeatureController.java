package by.bntu.eugenekulik.adit.controller;


import by.bntu.eugenekulik.adit.dto.FeatureDto;
import by.bntu.eugenekulik.adit.entity.Feature;
import by.bntu.eugenekulik.adit.service.FeatureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/feature", produces = "application/json")
public class FeatureController {

  private final FeatureService service;

  public FeatureController(FeatureService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<FeatureDto> createFeature(@RequestBody Feature feature){
    return service.saveFeature(feature)
        .map(FeatureDto::fromFeature)
        .map(ResponseEntity::ok)
        .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
  }

  @DeleteMapping("/{id}")
  public Long deleteFeature(@PathVariable Long id){
    service.deleteFeature(id);
    return id;
  }


  @GetMapping("/{id}")
  public ResponseEntity<FeatureDto> getFeature(@PathVariable Long id){
    return service.findFeature(id)
        .map(FeatureDto::fromFeature)
        .map(ResponseEntity::ok)
        .orElse(new ResponseEntity<>(null,HttpStatus.NOT_FOUND));
  }

  @GetMapping
  public Iterable<FeatureDto> getFeatures(@RequestParam Integer page){
    Page<Feature> result = service.findPage(page);
    return new PageImpl<>(service.findPage(page).stream()
        .map(FeatureDto::fromFeature)
        .toList(), PageRequest.of(page,10),result.getTotalElements());
  }


  @PatchMapping
  public ResponseEntity<FeatureDto> updateFeature(@RequestParam FeatureDto feature){
    return ResponseEntity
        .ok(FeatureDto
            .fromFeature(service
                .updateFeature(feature.toFeature())));
  }


  @GetMapping("/search")
  public Iterable<FeatureDto> findbyName(@RequestParam String name, @RequestParam Integer page){
    Page<Feature> result = service.findByNameContains(name, page);
    return new PageImpl<>(service.findByNameContains(name, page).stream()
        .map(FeatureDto::fromFeature)
        .toList(), PageRequest.of(page,10),result.getTotalElements());
  }
}