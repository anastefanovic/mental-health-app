package backend.adapters.driven.repository.implementation;

import backend.adapters.driven.transformer.AppointmentTransformer;
import backend.adapters.driven.transformer.TherapistTransformer;
import backend.domain.model.Appointment;
import backend.domain.model.Therapist;
import backend.ports.driven.AppointmentRepository;
import backend.adapters.driven.model.AppointmentJpa;
import backend.adapters.driven.model.TherapistJpa;
import backend.adapters.driven.repository.jpa.AppointmentJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AppointmentRepositoryImpl implements AppointmentRepository {
    private final AppointmentJpaRepository appointmentJpaRepository;
    private final AppointmentTransformer appointmentTransformer;
    private final TherapistTransformer therapistTransformer;

    public AppointmentRepositoryImpl(
            AppointmentJpaRepository appointmentJpaRepository,
            AppointmentTransformer appointmentTransformer,
            TherapistTransformer therapistTransformer
    ) {
        this.appointmentJpaRepository = appointmentJpaRepository;
        this.appointmentTransformer = appointmentTransformer;
        this.therapistTransformer = therapistTransformer;
    }

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentJpa appointmentJpa = appointmentTransformer.domainToEntity(appointment);
        AppointmentJpa savedAppointment = appointmentJpaRepository.save(appointmentJpa);
        return appointmentTransformer.entityToDomain(savedAppointment);
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        Optional<AppointmentJpa> appointmentJpa = appointmentJpaRepository.findById(id);
        return appointmentJpa.map(appointmentTransformer::entityToDomain);
    }

    @Override
    public List<Appointment> findAllByClientId(Long id) {
        List<AppointmentJpa> allAppointmentsJpa = appointmentJpaRepository.findAllByClientId(id);
        return allAppointmentsJpa.stream().map(appointmentTransformer::entityToDomain
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Appointment> findAllByTherapistId(Long id) {
        List<AppointmentJpa> allAppointmentsJpa = appointmentJpaRepository.findAllByTherapistId(id);
        return allAppointmentsJpa.stream().map(appointmentTransformer::entityToDomain
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Therapist> findAllByPastAppointmentWithClient(Long clientId, LocalDate currentDate, LocalTime currentTime) {
        List<TherapistJpa> allTherapistsJpa = appointmentJpaRepository.findAllByPastAppointmentWithClient(clientId, currentDate, currentTime);
        return allTherapistsJpa.stream().map(therapistTransformer::entityToDomain
        ).toList();
    }

    @Override
    public List<Appointment> findAllByTherapistIdAndSessionDate(Long id, LocalDate date) {
        List<AppointmentJpa> allAppointmentsJpa = appointmentJpaRepository.findAllByTherapistIdAndSessionDate(id, date);
        return allAppointmentsJpa.stream().map(appointmentTransformer::entityToDomain
        ).toList();
    }
}
