package by.bntu.eugenekulik.adit.security;

public interface JwtProvider {
  String generateToken(String username);

  boolean validateToken(String token);

  String getLoginFromToken(String token);
}
