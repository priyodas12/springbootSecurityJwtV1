package tech.security.springbootSecurityJwtV1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import tech.security.springbootSecurityJwtV1.exception.CustomAccessDeniedHandler;
import tech.security.springbootSecurityJwtV1.filter.JwtAuthenticationFilter;
import tech.security.springbootSecurityJwtV1.service.ApplicationUserDetailsService;

@Configuration
public class SecurityConfiguration {

  @Autowired
  private ApplicationUserDetailsService userDetailsService;

  @Autowired
  private CustomAccessDeniedHandler customAccessDeniedHandler;

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.csrf (AbstractHttpConfigurer::disable)
        .authorizeHttpRequests (matcherRegistry -> {
          //whitelisting below apis
          matcherRegistry.requestMatchers (
              "/public/home",
              "/registration/user",
              "/authenticate",
              "/secret-key"
                                          ).permitAll ();
          //role based authorization
          matcherRegistry.requestMatchers ("/api/home/admin").hasRole ("ADMIN");
          matcherRegistry.requestMatchers ("/api/home/user").hasRole ("USER");
          matcherRegistry.anyRequest ().authenticated ();
        })
        .formLogin (AbstractAuthenticationFilterConfigurer::permitAll)
        .exceptionHandling (
            exceptionHandlingConfigurer -> exceptionHandlingConfigurer.accessDeniedHandler (
                customAccessDeniedHandler))
        .addFilterBefore (jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build ();
  }

  @Bean
  public UserDetailsService userDetailsService () {
    return userDetailsService;
  }

  //custom authentication manager
  @Bean
  public AuthenticationManager authenticationManager () {
    return new ProviderManager (authenticationProvider ());
  }

  @Bean
  public AuthenticationProvider authenticationProvider () {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider ();
    authenticationProvider.setUserDetailsService (userDetailsService);
    authenticationProvider.setPasswordEncoder (passwordEncoder ());
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder () {
    return new BCryptPasswordEncoder (12);
  }
}
