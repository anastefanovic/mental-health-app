package backend.adapters.driving.model;

import java.time.LocalDate;

public class TherapistAndDateDto {
    private Long therapistId;
    private LocalDate date;

    public Long getTherapistId() { return therapistId; }

    public void setTherapistId(Long therapistId) { this.therapistId = therapistId; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }
}
