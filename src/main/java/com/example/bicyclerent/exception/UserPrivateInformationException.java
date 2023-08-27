package com.example.bicyclerent.exception;

public class UserPrivateInformationException extends RuntimeException {

  public UserPrivateInformationException() {
    super("User private information not found");
  }

}
