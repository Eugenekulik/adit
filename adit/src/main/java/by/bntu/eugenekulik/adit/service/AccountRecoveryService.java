package by.bntu.eugenekulik.adit.service;

public interface AccountRecoveryService {
  void createAccountRecoveryEvent(String email);

  void changePassword(String token, String password);
}
