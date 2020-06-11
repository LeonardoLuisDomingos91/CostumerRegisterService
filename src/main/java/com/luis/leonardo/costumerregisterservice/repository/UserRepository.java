package com.luis.leonardo.costumerregisterservice.repository;



import com.luis.leonardo.costumerregisterservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByLoginAndPassword(String login, String password);



    Optional<User> findByLogin(String login);
}
