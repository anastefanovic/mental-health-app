package backend.adapters.driving.model;

public class AppointmentDto {
    private Long id;
    private TherapistDto therapist;
    private ClientDto client;
    private String sessionType;
    private String reply = "PENDING";
    private String sessionDate;
    private String sessionTime;


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public TherapistDto getTherapist() { return therapist; }

    public void setTherapist(TherapistDto therapist) { this.therapist = therapist; }

    public ClientDto getClient() { return client; }

    public void setClient(ClientDto client) { this.client = client; }

    public String getSessionType() { return sessionType; }

    public void setSessionType(String sessionType) { this.sessionType = sessionType; }

    public String getReply() { return reply; }

    public void setReply(String reply) { this.reply = reply; }

    public String getSessionDate() { return sessionDate; }

    public void setSessionDate(String sessionDate) { this.sessionDate = sessionDate; }

    public String getSessionTime() { return sessionTime; }

    public void setSessionTime(String sessionTime) { this.sessionTime = sessionTime; }

    public AppointmentDto(
            Long id,
            TherapistDto therapist,
            ClientDto client,
            String sessionType,
            String reply,
            String sessionDate,
            String sessionTime
    ) {
        this.id = id;
        this.therapist = therapist;
        this.client = client;
        this.sessionType = sessionType;
        this.reply = reply;
        this.sessionDate = sessionDate;
        this.sessionTime = sessionTime;
    }

    public AppointmentDto() { }
}
