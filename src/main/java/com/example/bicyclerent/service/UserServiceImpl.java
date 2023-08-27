package com.example.bicyclerent.service;


import com.example.bicyclerent.dto.UserRegistrationDto;
import com.example.bicyclerent.dto.UserInfoDto;
import com.example.bicyclerent.model.Role;
import com.example.bicyclerent.model.User;
import com.example.bicyclerent.repository.RoleRepository;
import com.example.bicyclerent.repository.UserRepository;
import com.example.bicyclerent.service.interfaces.UserService;
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
public class UserServiceImpl implements UserDetailsService, UserService {

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

  @Override
  public User create(UserRegistrationDto userRegistrationDto) {
    Optional<User> existingUser = userRepository.findByUser(userRegistrationDto.getUsername());
    if (existingUser.isEmpty()) {
      return userRepository.save(User.builder()
          .id(0L)
          .user(userRegistrationDto.getUsername())
          .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
          .role("USER")
          .enabled(true)
          .build());
    } else {
      /**
       * Should be updated
       */
      throw new UsernameNotFoundException("User already exist");
    }
  }

  @Override
  public User get(Long id) {
    Optional<User> user = userRepository.findUserById(id);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UsernameNotFoundException("User not found");
    }
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public User update(UserRegistrationDto userRegistrationDto) {
    Optional<User> optionalUser = userRepository.findByUser(userRegistrationDto.getUsername());
    if (optionalUser.isPresent()) {
      return userRepository.save(User.builder()
          .id(optionalUser.get().getId())
          .user(userRegistrationDto.getUsername())
          .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
          .role(optionalUser.get().getRole())
          .enabled(true)
          .build());
    } else {
      throw new UsernameNotFoundException("User not found");
    }
  }

  @Override
  public boolean delete(Long id) {
    Optional<User> optionalUser = userRepository.findUserById(id);
    if (optionalUser.isPresent()) {
      userRepository.delete(optionalUser.get());
      return true;
    } else {
      throw new UsernameNotFoundException("User not found");
    }
  }

  @Override
  public User getUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> optionalUser = userRepository.findByUser(username);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new UsernameNotFoundException("User not found");
    }

  }

  public List<UserInfoDto> listUsers() {
    return userRepository.findAll().stream()
        .map(this::toInfoDto)
        .toList();
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
