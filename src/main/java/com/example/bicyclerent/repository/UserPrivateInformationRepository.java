package com.example.bicyclerent.repository;

import com.example.bicyclerent.model.User;
import com.example.bicyclerent.model.UserPrivateInformation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPrivateInformationRepository extends JpaRepository<UserPrivateInformation, Long> {

  Optional<UserPrivateInformation> findUserPrivateInformationByUser(User user);

}
