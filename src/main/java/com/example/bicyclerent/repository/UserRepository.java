package com.example.bicyclerent.repository;

import com.example.bicyclerent.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUser(String user);

  Optional<User> findUserById(Long id);

}
