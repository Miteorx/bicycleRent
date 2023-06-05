package com.example.bicyclerent.dto;

import com.example.bicyclerent.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPrivateInformationDto {
  private Long id;

  private String realName;

  private String realSurname;

  private String email;

  private String phoneNumber;

  private User user;

}
