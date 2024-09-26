package tech.security.springbootSecurityJwtV1.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import static java.time.ZoneOffset.UTC;
import lombok.extern.log4j.Log4j2;
import tech.security.springbootSecurityJwtV1.dao.SecureCredentialsRepository;
import tech.security.springbootSecurityJwtV1.model.ApplicationUser;
import tech.security.springbootSecurityJwtV1.model.SecureCredentials;

@Log4j2
@Service
public class JwtTokenService {

  //generate this by: SecretKeyController
  private static final String SECRET_KEY =
      "";

  @Autowired
  private SecureCredentialsRepository secureCredentialsRepository;

  public String generateToken (UserDetails userDetails, ApplicationUser applicationUser) {
    var email = applicationUser.getEmail ();
    var address = applicationUser.getAddress ();
    var expDate = applicationUser.getUserValidTillDate ();
    var phoneNumber = applicationUser.getPhoneNumber ();
    UUID userIdentifier = applicationUser.getUserIdentifier ();
    var createTimestamp = Instant.now ();
    Map<String, String> claims = Map.of ("iss", "https://private.auth-server.com",
                                         "Authorities", userDetails.getAuthorities ().toString (),
                                         "isActive", String.valueOf (userDetails.isEnabled ()),
                                         "email", email,
                                         "address", address,
                                         "expDate", expDate.toString (),
                                         "phoneNumber", phoneNumber,
                                         "userIdentifier", userIdentifier.toString ()
                                        );

    var jwtToken = Jwts.builder ()
        .subject (userDetails.getUsername ())//user specific info
        .claims (claims) //metadata
        .issuedAt (Date.from (createTimestamp)) //token issued at
        .expiration (Date.from (createTimestamp.plus (10, ChronoUnit.MINUTES))) //token expire at
        .signWith (secretKey ())
        .compact ();

    saveJwtToken (jwtToken, userDetails.getUsername (), userIdentifier, createTimestamp);

    return jwtToken;
  }

  private SecretKey secretKey () {
    var base64Encoded = Base64.getDecoder ().decode (SECRET_KEY);
    return Keys.hmacShaKeyFor (base64Encoded);
  }

  private void saveJwtToken (
      String JwtToken, String username, UUID userIdentifier,
      Instant createTimestamp
                            ) {
    var secureCredentials = SecureCredentials.builder ()
        .jwtToken (JwtToken)
        .username (username)
        .userIdentifier (userIdentifier)
        .createTimestamp (createTimestamp.atZone (UTC).toLocalDateTime ())
        .build ();
    var savedSecureCred = secureCredentialsRepository.save (secureCredentials);
    log.info ("saved secureCredentials:: {}", secureCredentials);
  }

  public String extractUsername (String jwtToken) {
    var jwsClaims = getClaims (jwtToken);

    return jwsClaims.getSubject ();
  }

  private Claims getClaims (String jwtToken) {
    return Jwts.parser ()
        .verifyWith (secretKey ())
        .build ()
        .parseSignedClaims (jwtToken)
        .getPayload ();
  }

  public boolean isJwtTokenValid (String jwtToken) {
    Claims claims = getClaims (jwtToken);
    return claims.getExpiration ().after (Date.from (Instant.now ()));
  }
}
