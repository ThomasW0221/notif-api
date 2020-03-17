package io.twinterf.notif.auth.repositories;

import io.twinterf.notif.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
