package com.example.bicyclerent.service;

import com.example.bicyclerent.dto.UserPrivateInformationDto;
import com.example.bicyclerent.model.User;
import com.example.bicyclerent.model.UserPrivateInformation;
import com.example.bicyclerent.repository.UserPrivateInformationRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrivateInformationService {

  private final UserPrivateInformationRepository userPrivateInformationRepository;

  public UserPrivateInformation saveInfo(User user,
      UserPrivateInformationDto userPrivateInformationDto) throws Exception {
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
      throw new Exception();
    }
  }

  public boolean updateInfo(User user, UserPrivateInformationDto userPrivateInformationDto)
      throws Exception {
    Optional<UserPrivateInformation> existingUserInfo = userPrivateInformationRepository.findUserPrivateInformationByUser(
        user);
    if (existingUserInfo.isPresent()) {
      userPrivateInformationRepository.save(UserPrivateInformation.builder()
          .id(existingUserInfo.get().getId())
          .realName(userPrivateInformationDto.getRealName())
          .realSurname(userPrivateInformationDto.getRealSurname())
          .email(userPrivateInformationDto.getEmail())
          .phoneNumber(userPrivateInformationDto.getPhoneNumber())
          .user(user)
          .build());
      return true;
    } else {
      throw new Exception();
    }
  }

  public UserPrivateInformation getUserPrivateInformation(User user) {
    Optional<UserPrivateInformation> userPrivateInformationOptional = userPrivateInformationRepository.findUserPrivateInformationByUser(
        user);
    if (userPrivateInformationOptional.isPresent()) {
      return userPrivateInformationOptional.get();
    } else {
      return userPrivateInformationRepository.save(UserPrivateInformation.builder()
          .id(null)
          .realName(user.getUser())
          .realSurname(user.getUser())
          .email(null)
          .phoneNumber(null)
          .user(user)
          .build());
    }
  }
}
