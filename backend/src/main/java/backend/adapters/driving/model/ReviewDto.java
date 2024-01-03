package backend.adapters.driving.model;


public class ReviewDto {
    private Long id;
    private String reviewMessage;
    private String date;
    private boolean isAccepted = false;
    private TherapistDto therapist;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getReviewMessage() { return reviewMessage; }

    public void setReviewMessage(String reviewMessage) { this.reviewMessage = reviewMessage; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public boolean isAccepted() { return isAccepted; }

    public void setAccepted(boolean accepted) { isAccepted = accepted; }

    public TherapistDto getTherapist() { return therapist; }

    public void setTherapist(TherapistDto therapist) { this.therapist = therapist; }

    public ReviewDto(
            Long id,
            String reviewMessage,
            String date,
            boolean isAccepted,
            TherapistDto therapist
    ) {
        this.id = id;
        this.reviewMessage = reviewMessage;
        this.date = date;
        this.isAccepted = isAccepted;
        this.therapist = therapist;
    }

    public ReviewDto() { }
}
