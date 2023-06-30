package by.bntu.eugenekulik.adit.service.impl;

import by.bntu.eugenekulik.adit.dao.jpa.UserRepository;
import by.bntu.eugenekulik.adit.dao.redis.UserIdRedisRepository;
import by.bntu.eugenekulik.adit.domain.amqp.AccountRecoveryEvent;
import by.bntu.eugenekulik.adit.domain.jpa.User;
import by.bntu.eugenekulik.adit.service.AccountRecoveryService;
import by.bntu.eugenekulik.adit.service.TokenGenerator;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class AccountRecoveryServiceImpl implements AccountRecoveryService {

  private final TokenGenerator tokenGenerator;

  private final UserIdRedisRepository repository;
  private final UserRepository userRepository;

  private final AmqpTemplate amqpTemplate;

  private final ResourceLoader resourceLoader;

  private final PasswordEncoder encoder;

  public AccountRecoveryServiceImpl(TokenGenerator tokenGenerator, UserIdRedisRepository repository, UserRepository userRepository, AmqpTemplate amqpTemplate, ResourceLoader resourceLoader, PasswordEncoder encoder) {
    this.tokenGenerator = tokenGenerator;
    this.repository = repository;
    this.userRepository = userRepository;
    this.amqpTemplate = amqpTemplate;
    this.resourceLoader = resourceLoader;
    this.encoder = encoder;
  }

  @Override
  public void createAccountRecoveryEvent(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isEmpty()) throw new UsernameNotFoundException("not found user with email address: " + email);
    Resource resource = resourceLoader.getResource("classpath:static/accountRecoveryMessageTemplate.txt");
    String message;
    try {
      message = new String(FileCopyUtils.copyToByteArray(resource.getFile()), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String token = tokenGenerator.get();
    amqpTemplate.convertAndSend("notification-service-exchange",
        "notification-service",
        new AccountRecoveryEvent(email,
            "account recovery",
            String.format(message, "<a href=\"http://localhost:4200/accountrecovery/password?token=" + token + "\">Ссылка для изменения пароля</a>")));
    repository.save(user.get().getUserId(), token);
  }

  @Override
  public void changePassword(String token, String password) {
    Long userId = repository.getUserIdByToken(token);
    if(userId == null) throw new IllegalArgumentException("invalid token");
    Optional<User> user = userRepository.findById(userId);
    if(user.isEmpty()) throw new UsernameNotFoundException(String.format("user with id %d not found", userId));
    user.get().setPassword(encoder.encode(password));
    userRepository.save(user.get());
  }
}
