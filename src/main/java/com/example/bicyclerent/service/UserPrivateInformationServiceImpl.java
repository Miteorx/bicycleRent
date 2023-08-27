package com.example.bicyclerent.service;

import com.example.bicyclerent.dto.UserPrivateInformationDto;
import com.example.bicyclerent.exception.UserPrivateInformationException;
import com.example.bicyclerent.model.User;
import com.example.bicyclerent.model.UserPrivateInformation;
import com.example.bicyclerent.repository.UserPrivateInformationRepository;
import com.example.bicyclerent.repository.UserRepository;
import com.example.bicyclerent.service.interfaces.UserPrivateInformationService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrivateInformationServiceImpl implements
    UserPrivateInformationService<UserPrivateInformation> {

  private final UserPrivateInformationRepository userPrivateInformationRepository;

  private final UserRepository userRepository;

  @Override
  public UserPrivateInformation create(User user,
      UserPrivateInformationDto userPrivateInformationDto) {
    Optional<UserPrivateInformation> existingUserInfo = userPrivateInformationRepository.findUserPrivateInformationByUser(
        user);
    if (existingUserInfo.isEmpty()) {
      return userPrivateInformationRepository.save(UserPrivateInformation.builder()
          .id(userPrivateInformationDto.getId())
          .realName(userPrivateInformationDto.getRealName())
          .realSurname(userPrivateInformationDto.getRealSurname())
          .email(userPrivateInformationDto.getEmail())
          .phoneNumber(userPrivateInformationDto.getPhoneNumber())
          .user(user)
          .build());
    } else {
      throw new UserPrivateInformationException();
    }
  }

  @Override
  public UserPrivateInformation get(Long id) {
    Optional<UserPrivateInformation> userPrivateInformationOptional = userPrivateInformationRepository.findById(
        id);
    if (userPrivateInformationOptional.isPresent()) {
      return userPrivateInformationOptional.get();
    }

    Optional<User> user = userRepository.findUserById(id);
    if (user.isPresent()) {
      return userPrivateInformationRepository.save(UserPrivateInformation.builder()
          .id(null)
          .realName(user.get().getUser())
          .realSurname(user.get().getUser())
          .email(null)
          .phoneNumber(null)
          .user(user.get())
          .build());
    } else {
      throw new UserPrivateInformationException();
    }
  }

  @Override
  public List<UserPrivateInformation> getAll() {
    return userPrivateInformationRepository.findAll();
  }

  @Override
  public UserPrivateInformation update(User user,
      UserPrivateInformationDto userPrivateInformationDto) {
    Optional<UserPrivateInformation> existingUserInfo = userPrivateInformationRepository.findUserPrivateInformationByUser(
        user);
    if (existingUserInfo.isPresent()) {
      return userPrivateInformationRepository.save(UserPrivateInformation.builder()
          .id(existingUserInfo.get().getId())
          .realName(userPrivateInformationDto.getRealName())
          .realSurname(userPrivateInformationDto.getRealSurname())
          .email(userPrivateInformationDto.getEmail())
          .phoneNumber(userPrivateInformationDto.getPhoneNumber())
          .user(user)
          .build());
    } else {
      throw new UserPrivateInformationException();
    }
  }

  @Override
  public boolean delete(Long id) {
    Optional<UserPrivateInformation> existingUserInfo = userPrivateInformationRepository.findById(
        id);
    if (existingUserInfo.isPresent()) {
      userPrivateInformationRepository.delete(existingUserInfo.get());
      return true;
    } else {
      throw new UserPrivateInformationException();
    }
  }
}
