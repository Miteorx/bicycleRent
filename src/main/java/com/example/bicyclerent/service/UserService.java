package com.example.bicyclerent.service;


import com.example.bicyclerent.dto.UserRegistrationDto;
import com.example.bicyclerent.dto.UserInfoDto;
import com.example.bicyclerent.model.Role;
import com.example.bicyclerent.model.User;
import com.example.bicyclerent.repository.RoleRepository;
import com.example.bicyclerent.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  PasswordEncoder passwordEncoder = PasswordEncoderFactories
      .createDelegatingPasswordEncoder();

  @PostConstruct
  public void init() {
    Optional<User> admin = userRepository.findByUser("admin");
    if (admin.isEmpty()) {
      userRepository.save(User.builder()
          .user("admin")
          .password(passwordEncoder.encode("adminsecret"))
          .role("ADMIN")
          .enabled(true)
          .build()
      );
    }

    Optional<User> apiman = userRepository.findByUser("apiman");
    if (apiman.isEmpty()) {
      userRepository.save(User.builder()
          .user("apiman")
          .password(passwordEncoder.encode("apisecret"))
          .role("API_USER")
          .enabled(true)
          .build()
      );
    }
  }

  public User getUserById(Long id) {
    Optional<User> user = userRepository.findUserById(id);
    if (user.isPresent()) {
      return user.get();
    }
    else throw new UsernameNotFoundException("User not found");
  }

  public User getUserByUsername(String user) throws UsernameNotFoundException {
    Optional<User> optionalUser = userRepository.findByUser(user);
    if (optionalUser.isPresent()){
      return optionalUser.get();
    }
    else throw new UsernameNotFoundException("User not found");

  }

  public List<UserInfoDto> listUsers() {
    return userRepository.findAll().stream()
        .map(this::toInfoDto)
        .toList();
  }

  public User saveUser(UserRegistrationDto userDto) throws Exception {
    Optional<User> existingUser = userRepository.findByUser(userDto.getUsername());
    if (existingUser.isEmpty()) {
      return userRepository.save(User.builder()
          .id(0L)
          .user(userDto.getUsername())
          .password(passwordEncoder.encode(userDto.getPassword()))
          .role("USER")
          .enabled(true)
          .build());
    } else {
      throw new Exception();
    }
  }

  private UserInfoDto toInfoDto(User user) {
    return UserInfoDto.builder()
        .id(user.getId())
        .username(user.getUser())
        .role(user.getRole())
        .enabled(user.isEnabled())
        .build();
  }

  @Override
  public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
    return userRepository.findByUser(user)
        .map(this::toUserDetails)
        .orElseThrow(() ->
            new UsernameNotFoundException("User with name '%s' not found".formatted(user)));
  }

  private UserDetails toUserDetails(User user) {
    return org.springframework.security.core.userdetails.User.withUsername(user.getUser())
        .password(user.getPassword())
        .authorities(collectAuthorities(user.getRole()))
        .disabled(!user.isEnabled())
        .build();
  }

  private List<GrantedAuthority> collectAuthorities(String role) {
    return roleRepository.getRole(role)
        .map(Role::getPrivileges)
        .stream().flatMap(Collection::stream)
        .map(priv -> (GrantedAuthority) new SimpleGrantedAuthority("PRIV_" + priv))
        .toList();
  }
}
