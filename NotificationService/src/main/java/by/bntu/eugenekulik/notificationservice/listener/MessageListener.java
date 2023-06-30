package by.bntu.eugenekulik.notificationservice.listener;

import by.bntu.eugenekulik.notificationservice.domain.AccountRecoveryEvent;
import by.bntu.eugenekulik.notificationservice.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {



  private final EmailService emailService;

  public MessageListener(EmailService emailService) {
    this.emailService = emailService;
  }


  @RabbitListener(queues = "notification-service")
  public void receiveMessage(AccountRecoveryEvent message){
    try {
      emailService.sendEmail(message);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }


}
