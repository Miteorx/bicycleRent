package com.example.bicyclerent.controller;

import com.example.bicyclerent.dto.UserPrivateInformationDto;
import com.example.bicyclerent.model.User;
import com.example.bicyclerent.service.UserPrivateInformationServiceImpl;
import com.example.bicyclerent.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

  private final UserServiceImpl userService;

  private final UserPrivateInformationServiceImpl userPrivateInformationServiceImpl;

  @GetMapping("/")
  public String main(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    model.addAttribute("title", "Main page");
    if (userDetails != null) {
      model.addAttribute("username", userDetails.getUsername());
      User user = userService.getUserByUsername(userDetails.getUsername());
      model.addAttribute("userId", user.getId());
    }
    return "index";
  }

  @GetMapping("/profile/{userId}")
  public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails,
      @PathVariable("userId") String userId) {

    String loggedInUsername = userDetails.getUsername();
    User loggedInUser = userService.getUserByUsername(loggedInUsername);

    if (loggedInUser.getId().equals(Long.valueOf(userId)) || loggedInUser.getRole().equals("ADMIN")) {
      User user = userService.get(Long.valueOf(userId));
      model.addAttribute("username", userDetails.getUsername());
      model.addAttribute("userId", user.getId());
      model.addAttribute("userinfo", userPrivateInformationServiceImpl.get(user.getId()));
      return "profile";
    } else {
      return "access-denied";
    }
  }

  @PostMapping("/profile/{userId}")
  public String profileUpdate(
      Model model,
      @AuthenticationPrincipal UserDetails userDetails,
      @PathVariable("userId") String userId,
      @RequestParam String name,
      @RequestParam String surname,
      @RequestParam String email,
      @RequestParam String phone) {

    User user = userService.get(Long.valueOf(userId));

    model.addAttribute("username", userDetails.getUsername());
    model.addAttribute("userId", user.getId());
    model.addAttribute("userinfo", userPrivateInformationServiceImpl.get(user.getId()));

    UserPrivateInformationDto userPrivateInformationDto = UserPrivateInformationDto.builder()
        .id(user.getId())
        .realName(name)
        .realSurname(surname)
        .email(email)
        .phoneNumber(phone).build();

    userPrivateInformationServiceImpl.update(user, userPrivateInformationDto);
    return "redirect:/profile/" + userId;
  }
}
