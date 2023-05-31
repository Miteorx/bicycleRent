package com.example.bicyclerent.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoDto {

  private Long id;

  private String username;

  private String role;

  private boolean enabled;

}
