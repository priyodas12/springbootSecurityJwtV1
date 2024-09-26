package tech.security.springbootSecurityJwtV1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.security.springbootSecurityJwtV1.model.ApplicationUser;
import tech.security.springbootSecurityJwtV1.service.ApplicationUserService;

//user registration
@RequestMapping ("/register")
@RestController
public class ApplicationUserRegistrationController {

  @Autowired
  private ApplicationUserService applicationUserService;

  @PostMapping ("/user")
  public ApplicationUser registerApplicationUser (@RequestBody ApplicationUser applicationUser) {
    return applicationUserService.saveApplicationUser (applicationUser);
  }
}
