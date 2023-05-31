package com.example.bicyclerent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

  @GetMapping("/")
  public String main(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    model.addAttribute("title", "Main page");
    if (userDetails != null) {
      model.addAttribute("username", userDetails.getUsername());
    }
    return "index";
  }
}
