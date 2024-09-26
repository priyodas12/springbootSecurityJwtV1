package tech.security.springbootSecurityJwtV1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessDeniedController {

  @GetMapping ("/access-denied")
  public ResponseEntity<String> accessDenied () {
    return ResponseEntity.status (403).body (
        "Access Denied: You do not have permission to access this resource.");
    
  }
}
