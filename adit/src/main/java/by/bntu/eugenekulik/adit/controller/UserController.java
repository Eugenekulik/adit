package by.bntu.eugenekulik.adit.controller;

import by.bntu.eugenekulik.adit.dto.UserDto;
import by.bntu.eugenekulik.adit.entity.User;
import by.bntu.eugenekulik.adit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserService userService;
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
    public ResponseEntity<UserDto> deleteUser(@RequestParam Long id){
        Optional<User> user = userService.deleteUser(id);
        return user
            .map(UserDto::fromUser)
            .map(ResponseEntity::ok)
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping
    public ResponseEntity<UserDto> updateUser(@RequestParam UserDto userDto) {

        Optional<User> user = userService.update(userDto.toUser());
        return user
            .map(UserDto::fromUser)
            .map(ResponseEntity::ok)
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
