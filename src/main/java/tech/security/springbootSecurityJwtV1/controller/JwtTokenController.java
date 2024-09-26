package tech.security.springbootSecurityJwtV1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tech.security.springbootSecurityJwtV1.model.LoginDetails;
import tech.security.springbootSecurityJwtV1.service.ApplicationUserDetailsService;
import tech.security.springbootSecurityJwtV1.service.JwtTokenService;

@RestController
public class JwtTokenController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenService jwtService;

  @Autowired
  private ApplicationUserDetailsService applicationUserDetailsService;

  @PostMapping ("/authenticate")
  public String getToken (@RequestBody LoginDetails loginDetails) {
    //authenticate user with credentials
    Authentication authentication =
        authenticationManager.authenticate (new UsernamePasswordAuthenticationToken (
            loginDetails.username (), loginDetails.password ()
        ));

    if (authentication.isAuthenticated ()) {
      // we could do this through applicationUserDetailsService.loadApplicationUser(username) here
      // we will pass email in the claims
      var applicationUser =
          applicationUserDetailsService.fetchApplicationUser (loginDetails.username ());
      var userDetails =
          applicationUserDetailsService.loadApplicationUser (applicationUser);

      return jwtService.generateToken (userDetails, applicationUser);
    }
    else {
      throw new UsernameNotFoundException ("Bad Credentials");
    }
  }
}
