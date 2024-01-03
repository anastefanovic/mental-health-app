package backend.adapters.driven.model;

import backend.domain.model.enums.AppointmentReply;
import backend.domain.model.enums.SessionType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "appointment")
@Entity
public class AppointmentJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionType sessionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentReply reply;

    @Column(nullable = false)
    private LocalDate sessionDate;

    @Column(nullable = false)
    private LocalTime sessionTime;

    @ManyToOne(targetEntity = TherapistJpa.class)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private TherapistJpa therapist;

    @ManyToOne(targetEntity = ClientJpa.class)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private ClientJpa client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public TherapistJpa getTherapist() {
        return therapist;
    }

    public void setTherapist(TherapistJpa therapist) {
        this.therapist = therapist;
    }

    public ClientJpa getClient() {
        return client;
    }

    public void setClient(ClientJpa client) {
        this.client = client;
    }

    public AppointmentJpa(Long id, SessionType sessionType, AppointmentReply reply, LocalDate sessionDate, LocalTime sessionTime, TherapistJpa therapist, ClientJpa client) {
        this.id = id;
        this.sessionType = sessionType;
        this.reply = reply;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
        this.therapist = therapist;
        this.client = client;
    }

    public AppointmentJpa() { }
}
