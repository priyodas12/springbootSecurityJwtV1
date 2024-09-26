package tech.security.springbootSecurityJwtV1.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table (name = "secure_credentials")
@Entity
public class SecureCredentials {

  @Id
  @GeneratedValue (strategy = GenerationType.UUID)
  @Column (name = "token_unique_id")
  private UUID tokenId;
  @Column (name = "jwt_token", length = 2000)
  private String jwtToken;
  private LocalDateTime createTimestamp;
  private String username;
  @Column (name = "application_user_identifier")
  private UUID userIdentifier;

}
