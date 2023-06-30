package by.bntu.eugenekulik.adit.controller;

import by.bntu.eugenekulik.adit.dto.ResponseMessage;
import by.bntu.eugenekulik.adit.dto.UserDto;
import by.bntu.eugenekulik.adit.domain.jpa.User;
import by.bntu.eugenekulik.adit.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<UserDto> getUsers(@RequestParam int page){
        Page<User> result = userService.getPage(page);
        return new PageImpl<>(userService.getPage(page).stream()
            .map(UserDto::fromUser).toList(),
            PageRequest.of(page,10),result.getTotalElements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user
            .map(UserDto::fromUser)
            .map(ResponseEntity::ok)
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("delete user with id: " + id);
    }

    @PatchMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity
            .ok(UserDto.fromUser(userService.update(userDto.toUser())));
    }

    @PatchMapping("/favourites")
    public ResponseEntity<ResponseMessage> addAdvertisementToFavourites(@RequestParam Long userId,
                                                              @RequestParam Long advertisementId) {
        userService.addAdvertisementToFavourites(userId,advertisementId);
        return ResponseEntity
            .ok(new ResponseMessage("add to favorites advertisement with id:" + advertisementId,200));
    }

    @DeleteMapping("/favourites")
    public ResponseEntity<ResponseMessage> deleteAdvertisementFromFavourites(@RequestParam Long userId,
                                                                             @RequestParam Long advertisementId) {
        userService.deleteAdvertisementFromFavourites(userId,advertisementId);
        return ResponseEntity
            .ok(new ResponseMessage("delete from favorites advertisement with id:" + advertisementId,200));
    }
}
