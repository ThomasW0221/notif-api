package io.twinterf.jwtloginsample.auth.repositories;

import io.twinterf.jwtloginsample.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
