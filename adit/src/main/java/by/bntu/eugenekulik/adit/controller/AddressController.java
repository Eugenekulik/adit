package by.bntu.eugenekulik.adit.controller;

import by.bntu.eugenekulik.adit.entity.Address;
import by.bntu.eugenekulik.adit.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/address", produces = "application/json")
public class AddressController {

  private AddressService addressService;

  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }


  @GetMapping
  public Iterable<Address> getPage(@RequestParam Integer page){
    return addressService.getPage(page);
  }


  @GetMapping("/{id}")
  public ResponseEntity<Address> getAddress(@PathVariable Long id){
    return addressService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
  }

  @PatchMapping
  public ResponseEntity<Address> updateAddress(@RequestBody Address address){
    return ResponseEntity.ok(addressService.updateAddress(address));
  }

  @DeleteMapping("/{id}")
  public Long deleteAddress(@PathVariable Long id){
    addressService.deleteAddress(id);
    return id;
  }


  @PostMapping
  public ResponseEntity<Address> createAddress(@RequestParam Address address){
    return ResponseEntity.ok(addressService.createAddress(address));
  }

}
