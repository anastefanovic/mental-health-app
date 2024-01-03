package backend.adapters.driven.model;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "review")
@Entity
public class ReviewJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String reviewMessage;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean isAccepted;

    @ManyToOne(targetEntity = TherapistJpa.class)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private TherapistJpa therapist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public TherapistJpa getTherapist() {
        return therapist;
    }

    public void setTherapist(TherapistJpa therapist) {
        this.therapist = therapist;
    }

    public ReviewJpa(Long id, String reviewMessage, LocalDate date, boolean isAccepted, TherapistJpa therapist) {
        this.id = id;
        this.reviewMessage = reviewMessage;
        this.date = date;
        this.isAccepted = isAccepted;
        this.therapist = therapist;
    }

    public ReviewJpa() { }
}
