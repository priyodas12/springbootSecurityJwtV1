package tech.security.springbootSecurityJwtV1.exception;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle (
      HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException
                     ) throws IOException, ServletException {
    RequestDispatcher dispatcher = request.getRequestDispatcher ("/access-denied");
    ((RequestDispatcher) dispatcher).forward (request, response);

  }
}

