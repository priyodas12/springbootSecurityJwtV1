package tech.security.springbootSecurityJwtV1.service;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts.SIG;
import jakarta.xml.bind.DatatypeConverter;

@Service
public class SecretKeyService {

  public String getSecretKey () {
    var securityKey = SIG.HS512.key ().build ();
    return
        DatatypeConverter.printHexBinary (securityKey.getEncoded ());
  }
}
