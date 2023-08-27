package com.example.bicyclerent.service.interfaces;

import com.example.bicyclerent.dto.UserRegistrationDto;
import com.example.bicyclerent.model.User;
import com.example.bicyclerent.service.interfaces.basic.BasicService;

public interface UserService extends BasicService<User, UserRegistrationDto> {

  User getUserByUsername(String username);


}
