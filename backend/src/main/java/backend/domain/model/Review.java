package backend.domain.model;

import java.time.LocalDate;

public class Review {
    private Long id;
    private String reviewMessage;
    private LocalDate date;
    private boolean isAccepted = false;
    private Therapist therapist;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getReviewMessage() { return reviewMessage; }

    public void setReviewMessage(String reviewMessage) { this.reviewMessage = reviewMessage; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public boolean isAccepted() { return isAccepted; }

    public void setAccepted(boolean accepted) { isAccepted = accepted; }

    public Therapist getTherapist() { return therapist; }

    public void setTherapist(Therapist therapist) { this.therapist = therapist; }

    public Review(
            Long id,
            String reviewMessage,
            LocalDate date,
            boolean isAccepted,
            Therapist therapist
    ) {
        this.id = id;
        this.reviewMessage = reviewMessage;
        this.date = date;
        this.isAccepted = isAccepted;
        this.therapist = therapist;
    }

    public Review() {}
}
