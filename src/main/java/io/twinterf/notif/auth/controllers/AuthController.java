package io.twinterf.notif.auth.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.twinterf.notif.JwtValueContainer;
import io.twinterf.notif.auth.entities.User;
import io.twinterf.notif.auth.pojos.CreateUserRequest;
import io.twinterf.notif.auth.pojos.LoginRequest;
import io.twinterf.notif.auth.pojos.TokenResponse;
import io.twinterf.notif.auth.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtValueContainer jwtValueContainer = new JwtValueContainer();

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    private UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/createUser")
    public ResponseEntity<URI> createUser(@RequestBody CreateUserRequest newUser) throws URISyntaxException {
        logger.info("Create User Requested " + newUser.toString());
        var user = new User();
        user.setEmail(newUser.getEmail());
        user.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt()));
        user.setUsername(newUser.getUsername());
        userRepository.save(user);
        logger.info("Create User Successful " + newUser.toString());
        return ResponseEntity.created(new URI("/users/" + user.getUsername())).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        logger.info("Login Requested " + login.toString());
        User requestedUser = userRepository.findById(login.getUsername()).orElse(null);
        if (requestedUser != null) {
            if(!BCrypt.checkpw(login.getPassword(), requestedUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            String token = JWT.create()
                    .withIssuer(jwtValueContainer.getIssuerValue())
                    .withClaim("user", requestedUser.getUsername())
                    .withExpiresAt(Date.from(Instant.now().plusMillis(300000)))
                    .sign(Algorithm.HMAC256(jwtValueContainer.getSignatureValue()));

            return ResponseEntity.ok().body(new TokenResponse(token));
        } else {
            return ResponseEntity.badRequest().build();
        }

    }
}
