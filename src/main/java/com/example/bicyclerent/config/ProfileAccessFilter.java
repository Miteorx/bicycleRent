package com.example.bicyclerent.config;

import com.example.bicyclerent.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class ProfileAccessFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {
      String userId = request.getRequestURI().substring("/profile/".length());
      User authenticatedUser = (User) authentication.getPrincipal();

      if (userId.equals(authenticatedUser.getId().toString())) {
        filterChain.doFilter(request, response);
      }
    }
  }
}
