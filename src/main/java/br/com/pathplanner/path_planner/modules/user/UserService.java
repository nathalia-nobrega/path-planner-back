package br.com.pathplanner.path_planner.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public Optional<User> findUserByEmail(String email) {
        return this.repository.findByEmail(email);

    }

}
