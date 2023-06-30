package by.bntu.eugenekulik.adit.dto;

import lombok.Data;

@Data
public class JwtAuthorizationDto {
  private final String token;
  private final UserDto user;

  private final String message;

  public JwtAuthorizationDto(String token, UserDto user, String message) {
    this.token = token;
    this.user = user;
    this.message = message;
  }

  public static JwtAuthorizationDto error(String message){
    return new JwtAuthorizationDto("",null,message);
  }
}
