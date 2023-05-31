package com.example.bicyclerent.repository;

import com.example.bicyclerent.model.Role;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository {

  private static final Map<String, Role> ROLES = Stream.of(
          Role.builder()
              .id("USER")
              .privileges(List.of())
              .build(),
          Role.builder()
              .id("ADMIN")
              .privileges(List.of("USER_MANAGEMENT"))
              .build(),
          Role.builder()
              .id("API_USER")
              .privileges(List.of("API_ACCESS"))
              .build()
      )
      .collect(Collectors.toUnmodifiableMap(
          Role::getId,
          Function.identity())
      );

  public Optional<Role> getRole(String id) {
    return Optional.ofNullable(ROLES.get(id));
  }

}
