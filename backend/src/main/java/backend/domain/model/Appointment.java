package backend.domain.model;

import backend.domain.model.enums.AppointmentReply;
import backend.domain.model.enums.SessionType;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private Long id;
    private Therapist therapist;
    private Client client;
    private SessionType sessionType;
    private AppointmentReply reply = AppointmentReply.PENDING;
    private LocalDate sessionDate;
    private LocalTime sessionTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Therapist getTherapist() {
        return therapist;
    }

    public void setTherapist(Therapist therapist) {
        this.therapist = therapist;
    }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public AppointmentReply getReply() {
        return reply;
    }

    public void setReply(AppointmentReply reply) {
        this.reply = reply;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public LocalTime getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(LocalTime sessionTime) {
        this.sessionTime = sessionTime;
    }

    public Appointment(Long id, Therapist therapist, Client client, SessionType sessionType, AppointmentReply reply, LocalDate sessionDate, LocalTime sessionTime) {
        this.id = id;
        this.therapist = therapist;
        this.client = client;
        this.sessionType = sessionType;
        this.reply = reply;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
    }

    public Appointment() { }
}
