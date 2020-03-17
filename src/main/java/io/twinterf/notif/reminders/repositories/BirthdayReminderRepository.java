package io.twinterf.notif.reminders.repositories;

import io.twinterf.notif.reminders.entities.BirthdayReminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BirthdayReminderRepository extends JpaRepository<BirthdayReminder, Long> {
    List<BirthdayReminder> findByUserId(String userId);
}
