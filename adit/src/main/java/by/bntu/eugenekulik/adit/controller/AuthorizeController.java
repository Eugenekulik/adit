package by.bntu.eugenekulik.adit.controller;

import by.bntu.eugenekulik.adit.dto.JwtAuthorizationDto;
import by.bntu.eugenekulik.adit.dto.UserDto;
import by.bntu.eugenekulik.adit.domain.jpa.User;
import by.bntu.eugenekulik.adit.security.JwtProvider;
import by.bntu.eugenekulik.adit.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin("*")
public class AuthorizeController {

  private final UserService userService;
  private final JwtProvider provider;

  public AuthorizeController(UserService userService, JwtProvider provider) {
    this.userService = userService;
    this.provider = provider;
  }

  private record AuthUserDetails(String login, String password){}

  @PostMapping(value = "/auth")
  public @ResponseBody JwtAuthorizationDto authorize(@RequestBody AuthUserDetails authUserDetails) {
    User user = userService.authorize(authUserDetails.login, authUserDetails.password);
    if (user != null) {
      return new JwtAuthorizationDto(provider.generateToken(authUserDetails.login), UserDto.fromUser(user), "");
    } else return JwtAuthorizationDto.error("user name or password is wrong");
  }
  @PostMapping(value = "/register")
  public @ResponseBody JwtAuthorizationDto registrate(@RequestBody User user) {
    if (user != null) {
      Optional<User> result = userService.create(user);
      return result
          .map(value ->new JwtAuthorizationDto(provider.generateToken(user.getLogin()), UserDto.fromUser(value), ""))
          .orElse(null);
    } else return null;
  }
}
