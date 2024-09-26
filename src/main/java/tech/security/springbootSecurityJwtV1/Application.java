package tech.security.springbootSecurityJwtV1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.extern.log4j.Log4j2;
import tech.security.springbootSecurityJwtV1.service.ApplicationUserService;

@Log4j2
@EnableWebSecurity
@SpringBootApplication
public class Application {

  @Autowired
  private ApplicationUserService applicationUserService;

  public static void main (String[] args) {
    SpringApplication.run (Application.class, args);
  }

  @Bean
  CommandLineRunner initDatabase () {
    return args -> {
      var savedUser = applicationUserService.savePredefinedUser ().stream ().toList ();

      log.info ("Saved Application User: {}", savedUser);
    };
  }
}
