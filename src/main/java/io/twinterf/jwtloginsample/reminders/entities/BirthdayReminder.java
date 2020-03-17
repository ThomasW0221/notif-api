package io.twinterf.jwtloginsample.reminders.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BirthdayReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reminderId;

    private String subjectName;

    @Temporal(TemporalType.DATE)
    private Date subjectBirthday;

    private String userId;

    public BirthdayReminder() {
    }

    public Long getReminderId() {
        return reminderId;
    }

    public void setReminderId(Long reminderId) {
        this.reminderId = reminderId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Date getSubjectBirthday() {
        return subjectBirthday;
    }

    public void setSubjectBirthday(Date subjectBirthday) {
        this.subjectBirthday = subjectBirthday;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BirthdayReminder{" +
                "reminderId=" + reminderId +
                ", subjectname='" + subjectName + '\'' +
                ", subjectBirthday=" + subjectBirthday +
                ", userId=" + userId +
                '}';
    }
}
