package backend.ports.driven;

import backend.domain.model.Appointment;
import backend.domain.model.Therapist;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    List<Appointment> findAllByClientId(Long id);
    List<Appointment> findAllByTherapistId(Long id);
    List<Therapist> findAllByPastAppointmentWithClient(Long clientId, LocalDate currentDate, LocalTime currentTime);
    List<Appointment> findAllByTherapistIdAndSessionDate(Long id, LocalDate date);
}
