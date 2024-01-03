package backend.domain.service;

import backend.adapters.driving.exception.DataTypeNotSupportedException;
import backend.adapters.driving.exception.ResourceNotFoundException;
import backend.domain.model.Appointment;
import backend.domain.model.Client;
import backend.domain.model.Therapist;
import backend.domain.model.User;
import backend.domain.model.enums.AppointmentReply;
import backend.ports.driving.AppointmentService;
import backend.ports.driven.AppointmentRepository;
import org.springframework.data.util.Pair;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DomainAppointmentService implements AppointmentService {

    private static final List<LocalTime> WORKING_HOURS = Arrays.asList(
            LocalTime.parse("08:00"),
            LocalTime.parse("09:00"),
            LocalTime.parse("10:00"),
            LocalTime.parse("11:00"),
            LocalTime.parse("12:00"),
            LocalTime.parse("13:00"),
            LocalTime.parse("14:00"),
            LocalTime.parse("15:00"),
            LocalTime.parse("16:00"),
            LocalTime.parse("17:00"),
            LocalTime.parse("18:00"),
            LocalTime.parse("19:00"),
            LocalTime.parse("20:00")
    );

    private final AppointmentRepository appointmentRepository;

    public DomainAppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment setAppointmentReply(Pair<Long, AppointmentReply> appointmentReply) {
        Appointment toUpdate = appointmentRepository.findById(appointmentReply.getFirst())
                .orElseThrow(() -> {
                   throw new ResourceNotFoundException(
                           "Appointment with id " +  appointmentReply.getFirst() + " not found"
                           );
                });

        toUpdate.setReply(appointmentReply.getSecond());
        return appointmentRepository.save(toUpdate);
    }

    @Override
    public Appointment createNewAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // if appointment doesn't exist OR (appointment not ACCEPTED AND appointment not PENDING)
    @Override
    public List<LocalTime> getAvailableWorkingHours(Long therapistId, LocalDate date) {
        List<Appointment> allAppointments = appointmentRepository
                .findAllByTherapistIdAndSessionDate(
                        therapistId,
                        date
                );

        List<LocalTime> allBookedHours = allAppointments.stream()
                .map(
                    Appointment::getSessionTime
        ).toList();

        return WORKING_HOURS.stream().filter(
                i -> (!(allBookedHours.contains(i)) || notAcceptedOrPending(allAppointments, i))
        ).toList();

    }

    @Override
    public List<Appointment> getAllAppointments(User user) {
        List<Appointment> allAppointments = null;

        if(user instanceof Client) {
            allAppointments = appointmentRepository.findAllByClientId(user.getId());
        }
        else if(user instanceof Therapist) {
            allAppointments = appointmentRepository.findAllByTherapistId(user.getId());
        }
        else throw new DataTypeNotSupportedException("User type not supported");

        sortAppointmentsByDateAndTime(allAppointments);

        return allAppointments;
    }

    // Therapists that have at least one past appointment with the given client
    @Override
    public List<Therapist> getAllTherapistsToReview(Long clientId) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        return appointmentRepository.findAllByPastAppointmentWithClient(clientId, currentDate, currentTime);
    }

    private boolean notAcceptedOrPending(List<Appointment> appointments, LocalTime time) {
        List<Appointment> filtered = appointments.stream().filter( app ->
                (
                        app.getSessionTime().equals(time)
                                && (app.getReply().equals(AppointmentReply.ACCEPTED)
                                || app.getReply().equals(AppointmentReply.PENDING))
                )
        ).toList();

        return filtered.isEmpty();
    }

    private void sortAppointmentsByDateAndTime(List<Appointment> unsorted) {
        Comparator<Appointment> dateTimeComparator = Comparator
                .comparing(Appointment::getSessionDate)
                .thenComparing(Appointment::getSessionTime);

        unsorted.sort(dateTimeComparator);
    }

}
