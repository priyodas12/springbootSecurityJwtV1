package tech.security.springbootSecurityJwtV1.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import tech.security.springbootSecurityJwtV1.dao.ApplicationUserRepository;
import tech.security.springbootSecurityJwtV1.model.ApplicationUser;

@Service
public class ApplicationUserService {

  @Autowired
  private Faker faker;

  @Autowired
  private ApplicationUserRepository applicationUserRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  //generation of basic user on application boot up
  public List<ApplicationUser> savePredefinedUser () {
    var basicUser = ApplicationUser
        .builder ()
        .username ("johnwink")
        .password ("")//password string
        .email ("johnwink@example.com")
        .userValidTillDate (LocalDateTime.now ().plusYears (7))
        .userIdentifier (UUID.randomUUID ())
        .address (faker.address ().fullAddress ())
        .phoneNumber (faker.phoneNumber ().phoneNumber ())
        .role (List.of ("USER"))
        .build ();
    var savedBasicUser = saveApplicationUser (basicUser);

    var basicAdmin = ApplicationUser
        .builder ()
        .username ("johndoe")
        .password ("")//password string
        .email ("johndoe@example.com")
        .userValidTillDate (LocalDateTime.now ().plusYears (7))
        .userIdentifier (UUID.randomUUID ())
        .address (faker.address ().fullAddress ())
        .phoneNumber (faker.phoneNumber ().phoneNumber ())
        .role (List.of ("ADMIN"))
        .build ();
    var savedBasicAdmin = saveApplicationUser (basicAdmin);

    return List.of (basicAdmin, basicUser);
  }

  public ApplicationUser saveApplicationUser (ApplicationUser applicationUser) {
    applicationUser.setPassword (passwordEncoder.encode (applicationUser.getPassword ()));
    return applicationUserRepository.save (applicationUser);
  }
}
