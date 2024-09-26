package tech.security.springbootSecurityJwtV1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import tech.security.springbootSecurityJwtV1.dao.ApplicationUserRepository;
import tech.security.springbootSecurityJwtV1.model.ApplicationUser;

@Log4j2
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

  @Autowired
  private ApplicationUserRepository applicationUserRepository;

  @Override
  public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
    var user = fetchApplicationUser (username);
    log.info ("Fetched User: {} by username: {}", user, username);
    return User.builder ()
        .username (user.getUsername ())
        .password (user.getPassword ())
        .roles (String.join (",", user.getRole ()))
        .build ();

  }

  //for custom claims
  public ApplicationUser fetchApplicationUser (String username) {
    Optional<ApplicationUser> applicationUserOptional =
        applicationUserRepository.findByUsername (username);

    if (applicationUserOptional.isPresent ()) {
      return applicationUserOptional.get ();
    }
    else {
      throw new UsernameNotFoundException ("Username %s not found!".formatted (username));
    }
  }

  public UserDetails loadApplicationUser (ApplicationUser applicationUser)
      throws UsernameNotFoundException {
    log.info ("Fetched applicationUser {}", applicationUser);
    return User.builder ()
        .username (applicationUser.getUsername ())
        .password (applicationUser.getPassword ())
        .roles (String.join (",", applicationUser.getRole ()))
        .build ();

  }
}
