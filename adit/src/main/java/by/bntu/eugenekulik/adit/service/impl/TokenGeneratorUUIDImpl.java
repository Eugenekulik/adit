package by.bntu.eugenekulik.adit.service.impl;

import by.bntu.eugenekulik.adit.service.TokenGenerator;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class TokenGeneratorUUIDImpl implements TokenGenerator {
  @Override
  public String get() {
    return UUID.randomUUID().toString();
  }
}
