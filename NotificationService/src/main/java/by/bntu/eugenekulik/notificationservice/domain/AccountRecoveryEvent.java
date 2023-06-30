package by.bntu.eugenekulik.notificationservice.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRecoveryEvent implements Serializable {

  private String email;

  private String subject;
  private String message;
}
