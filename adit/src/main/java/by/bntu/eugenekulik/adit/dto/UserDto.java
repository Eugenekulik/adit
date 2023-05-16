package by.bntu.eugenekulik.adit.dto;

import by.bntu.eugenekulik.adit.entity.Role;
import by.bntu.eugenekulik.adit.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties
public class UserDto {

  private Long id;
  private String login;
  private String firstName;
  private String lastName;
  private LocalDate age;
  private String email;
  private String phone;

  private List<Role> roles;

  public User toUser(){
    User user = new User();
    user.setUserId(id);
    user.setLogin(login);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setPhone(phone);
    user.setAge(age);
    user.setRoles(roles);
    return user;
  }

  public static UserDto fromUser(User user){
    return UserDto.builder()
        .id(user.getUserId())
        .age(user.getAge())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .login(user.getLogin())
        .lastName(user.getLastName())
        .phone(user.getPhone())
        .roles(user.getRoles())
        .build();
  }
}
