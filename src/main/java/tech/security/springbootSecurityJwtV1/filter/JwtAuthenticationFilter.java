package tech.security.springbootSecurityJwtV1.filter;

import java.io.IOException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.security.springbootSecurityJwtV1.service.ApplicationUserDetailsService;
import tech.security.springbootSecurityJwtV1.service.JwtTokenService;
import tech.security.springbootSecurityJwtV1.util.Constant;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtTokenService jwtTokenService;

  @Autowired
  private ApplicationUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal (
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
                                  ) throws ServletException, IOException {
    //extract bearer token from Authorization header
    var authHeader = request.getHeader (Constant.AUTHORIZATION);
    if (StringUtils.isEmpty (authHeader) || !authHeader.startsWith (Constant.BEARER_TOKEN)) {
      filterChain.doFilter (request, response);
      return;
    }

    var jwtToken = authHeader.substring (Constant.BEARER_TOKEN.length ());

    //extract username from jwtToken
    var username = jwtTokenService.extractUsername (jwtToken);

    //check authentication context is empty
    if (StringUtils.isNotEmpty (username) && ObjectUtils.isEmpty (
        SecurityContextHolder.getContext ().getAuthentication ())) {
      //extract user details
      UserDetails userDetails = userDetailsService.loadUserByUsername (username);
      if (ObjectUtils.isNotEmpty (userDetails) && jwtTokenService.isJwtTokenValid (jwtToken)) {
        //post expiration validation create UsernamePasswordAuthenticationToken and persist into
        // SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken (
                username, userDetails.getPassword (), userDetails.getAuthorities ()
            );
        //track down loggedIn user
        authenticationToken.setDetails (
            new WebAuthenticationDetailsSource ().buildDetails (request));
        SecurityContextHolder.getContext ().setAuthentication (authenticationToken);
      }
    }
  }
}
