package com.compulynx.compas.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebConfig extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain filterChain)
      throws ServletException, IOException {
            response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");

         //   response.addHeader("Access-Control-Allow-Origin", "*");
//            response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, HEAD");
            response.addHeader("Access-Control-Allow-Headers", "headers,x-xsrf-token, Origin, Accept, X-Requested-With,Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, principal, jwtAuthorization");
            response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin,Access-Control-Allow-Credentials, jwtAuthorization");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addIntHeader("Access-Control-Max-Age", 10);

    response.setHeader("Strict-Transport-Security", "max-age=31536000 ; includeSubDomains");
    response.setHeader("X-Content-Type-Options", "nosniff");
    response.setHeader("X-Frame-Options", "DENY");
    response.setHeader("X-XSS-Protection", "1; mode=block");
    // response.setHeader("Content-Security-Policy", "default-src 'self' data:; style-src
    // 'self' 'unsafe-inline'");
    response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    filterChain.doFilter(request, response);
  }
}
