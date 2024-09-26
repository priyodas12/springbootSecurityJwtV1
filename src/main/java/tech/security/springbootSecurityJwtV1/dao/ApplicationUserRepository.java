package tech.security.springbootSecurityJwtV1.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.security.springbootSecurityJwtV1.model.ApplicationUser;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {

  Optional<ApplicationUser> findByUsername (String username);
}
