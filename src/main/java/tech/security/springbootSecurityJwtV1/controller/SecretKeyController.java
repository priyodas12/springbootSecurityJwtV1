package tech.security.springbootSecurityJwtV1.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.security.springbootSecurityJwtV1.service.SecretKeyService;

@RestController
public class SecretKeyController {

  @Autowired
  private SecretKeyService secretKeyService;

  //one time activity for generating secret key for JWT token generation and validation
  @GetMapping ("/secret-key")
  public ResponseEntity<Map<String, String>> getSecretKey () {

    var encodedSecretKey = secretKeyService.getSecretKey ();
    return ResponseEntity.ok (Map.of ("time", LocalDateTime.now ().toString (),
                                      "secretKey", encodedSecretKey
                                     ));
  }
}
