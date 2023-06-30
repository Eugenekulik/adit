package by.bntu.eugenekulik.aditeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ApplicationEurekaServer {
  public static void main(String[] args) {
    SpringApplication.run(ApplicationEurekaServer.class, args);
  }

}
