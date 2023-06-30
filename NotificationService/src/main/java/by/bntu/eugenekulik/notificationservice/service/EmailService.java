package by.bntu.eugenekulik.notificationservice.service;

import by.bntu.eugenekulik.notificationservice.domain.AccountRecoveryEvent;
import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private final JavaMailSender mailSender;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendEmail(AccountRecoveryEvent emailBody) throws MessagingException {
    MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(),true);
    helper.setFrom("aditserver@mail.ru");
    helper.setTo(emailBody.getEmail());
    helper.setSubject(emailBody.getSubject());
    helper.setText(emailBody.getMessage(),true);
    helper.setReplyTo("aditserver@mail.ru");
    mailSender.send(helper.getMimeMessage());
  }
}