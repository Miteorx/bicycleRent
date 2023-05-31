package com.example.bicyclerent.controller;

import com.example.bicyclerent.dto.UserRegistrationDto;
import com.example.bicyclerent.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

  private final UserService userService;

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @PostMapping("/login")
  public String loginPost(@RequestParam String username, @RequestParam String password) {

    return "redirect:/";
  }

  @GetMapping("/registration")
  public String registration() {
    return "registration";
  }

  @PostMapping("/registration")
  public String registrationPost(@RequestParam String username, @RequestParam String password)
      throws Exception {
    userService.saveUser(UserRegistrationDto.builder().username(username).password(password).build());
    return "redirect:/login";
  }
}
