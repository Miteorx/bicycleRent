package com.example.bicyclerent.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Role {

  private String id;
  private List<String> privileges;

}
