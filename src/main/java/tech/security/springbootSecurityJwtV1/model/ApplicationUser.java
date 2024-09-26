package tech.security.springbootSecurityJwtV1.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table (name = "application_user")
public class ApplicationUser {

  @Id
  @GeneratedValue (strategy = GenerationType.SEQUENCE)
  private Long id;
  private String username;
  private String password;
  private String email;
  private LocalDateTime userValidTillDate;
  private UUID userIdentifier;
  private String address;
  private String phoneNumber;
  private List<String> role;
}
