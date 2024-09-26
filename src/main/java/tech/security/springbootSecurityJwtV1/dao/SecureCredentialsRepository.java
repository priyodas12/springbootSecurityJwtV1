package tech.security.springbootSecurityJwtV1.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.security.springbootSecurityJwtV1.model.SecureCredentials;

@Repository
public interface SecureCredentialsRepository extends JpaRepository<SecureCredentials, UUID> {
}
