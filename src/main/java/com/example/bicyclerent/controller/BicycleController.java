package com.example.bicyclerent.controller;

import com.example.bicyclerent.model.User;
import com.example.bicyclerent.service.UserPrivateInformationServiceImpl;
import com.example.bicyclerent.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bicycle")
public class BicycleController {

  private final UserServiceImpl userService;

  private final UserPrivateInformationServiceImpl userPrivateInformationServiceImpl;

  @GetMapping("")
  public String main(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    model.addAttribute("title", "Main page");
    if (userDetails != null) {
      model.addAttribute("username", userDetails.getUsername());
      User user = userService.getUserByUsername(userDetails.getUsername());
      model.addAttribute("userId", user.getId());
    }
    return "bicycle-add";
  }

}
