package tech.security.springbootSecurityJwtV1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

@Configuration
public class CustomConfig {

  //faker for random claims generation
  @Bean
  public Faker faker () {
    return new Faker ();
  }
}
