package by.bntu.eugenekulik.adit.domain.amqp;


import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRecoveryEvent implements Serializable {
  private String email;

  private String subject;
  private String message;
}
