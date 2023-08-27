package com.example.bicyclerent.service.interfaces;

import com.example.bicyclerent.dto.UserPrivateInformationDto;
import com.example.bicyclerent.model.User;
import java.util.List;

public interface UserPrivateInformationService<T> {
  T create(User user, UserPrivateInformationDto userPrivateInformationDto);

  T get(Long id);

  List<T> getAll();

  T update(User user, UserPrivateInformationDto userPrivateInformationDto);

  boolean delete(Long id);
}
