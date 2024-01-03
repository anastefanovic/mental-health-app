package backend.ports.driving;

import backend.domain.model.Appointment;
import backend.domain.model.Therapist;
import backend.domain.model.User;
import backend.domain.model.enums.AppointmentReply;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {
    Appointment setAppointmentReply(Pair<Long, AppointmentReply> appointmentReply);
    Appointment createNewAppointment(Appointment appointment);
    List<LocalTime> getAvailableWorkingHours(Long therapistId, LocalDate date);
    List<Appointment> getAllAppointments(User user);
    List<Therapist> getAllTherapistsToReview(Long clientId);
}
