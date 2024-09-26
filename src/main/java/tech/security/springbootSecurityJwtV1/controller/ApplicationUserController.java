package tech.security.springbootSecurityJwtV1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApplicationUserController {

  //roles based apis
  @GetMapping ("/public/home")
  public ResponseEntity<String> publicWelcomePage () {
    return ResponseEntity.ok ("Welcome to Home!");
  }


  @GetMapping ("/api/home/user")
  public ResponseEntity<String> userWelcomePage () {
    return ResponseEntity.ok ("Welcome to User Home!");
  }


  @GetMapping ("/api/home/admin")
  public ResponseEntity<String> adminWelcomePage () {
    return ResponseEntity.ok ("Welcome to Admin Home!");
  }

}
