package io.twinterf.jwtloginsample.reminders.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.twinterf.jwtloginsample.JwtValueContainer;
import io.twinterf.jwtloginsample.reminders.entities.BirthdayReminder;
import io.twinterf.jwtloginsample.reminders.repositories.BirthdayReminderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/birthday")
public class BirthdayReminderController {

    Logger logger = LoggerFactory.getLogger(BirthdayReminderController.class);
    private JwtValueContainer jwtValueContainer = new JwtValueContainer();
    private JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtValueContainer.getSignatureValue()))
            .withIssuer(jwtValueContainer.getIssuerValue())
            .build();

    private BirthdayReminderRepository birthdayReminderRepository;

    @Autowired
    public BirthdayReminderController(BirthdayReminderRepository birthdayReminderRepository) {
        this.birthdayReminderRepository = birthdayReminderRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getBirthdayRemindersForUser(@RequestHeader("Authorization") String token,
                                                         @PathVariable String userId) {
        if(token == null) {
            return ResponseEntity.badRequest().build();
        }
        token = token.split(" ")[1];
        DecodedJWT decodedJWT;
        try {
            decodedJWT = jwtVerifier.verify(token);
        } catch(JWTDecodeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(userId.equals(decodedJWT.getClaim("user").asString())) {
            List<BirthdayReminder> reminders = birthdayReminderRepository.findByUserId(userId);
            return ResponseEntity.ok(reminders);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> postBirthdayReminderForUser(@RequestHeader(value = "Authorization") String token,
                                                         @PathVariable String userId,
                                                         @RequestBody BirthdayReminder birthdayReminder) {
        if(token == null) {
            return ResponseEntity.badRequest().build();
        }
        token = token.split(" ")[1];
        DecodedJWT decodedJWT;
        try {
            decodedJWT = jwtVerifier.verify(token);
        } catch(JWTDecodeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(userId.equals(decodedJWT.getClaim("user").asString())) {
            birthdayReminder.setUserId(userId);
            birthdayReminderRepository.save(birthdayReminder);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
