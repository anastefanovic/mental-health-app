package backend.adapters.driving.controller;

import backend.adapters.driving.transformer.DataFormatter;
import backend.adapters.driving.model.AppointmentDto;
import backend.adapters.driving.model.TherapistAndDateDto;
import backend.adapters.driving.model.TherapistDto;
import backend.adapters.driving.model.UserDto;
import backend.adapters.driving.transformer.AppointmentDtoTransformer;
import backend.adapters.driving.transformer.TherapistDtoTransformer;
import backend.adapters.driving.transformer.UserDtoTransformer;
import backend.domain.model.Appointment;
import backend.domain.model.Therapist;
import backend.domain.model.User;
import backend.domain.model.enums.AppointmentReply;
import backend.ports.driving.AppointmentService;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@CrossOrigin
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentDtoTransformer appointmentTransformer;
    private final UserDtoTransformer userTransformer;
    private final TherapistDtoTransformer therapistTransformer;

    public AppointmentController(
            AppointmentService appointmentService,
            AppointmentDtoTransformer appointmentTransformer,
            UserDtoTransformer userTransformer,
            TherapistDtoTransformer therapistTransformer
    ) {
        this.appointmentService = appointmentService;
        this.appointmentTransformer = appointmentTransformer;
        this.userTransformer = userTransformer;
        this.therapistTransformer = therapistTransformer;
    }

    @PostMapping("/create")
    AppointmentDto createNewAppointment(@RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = appointmentTransformer.toDomain(appointmentDto);
        Appointment returnValue = appointmentService.createNewAppointment(appointment);
        return appointmentTransformer.fromDomain(returnValue);
    }

    @PostMapping("/get-available-hours")
    List<String> getAvailableWorkingHours(@RequestBody TherapistAndDateDto therapistAndDateDto) {
        return appointmentService.getAvailableWorkingHours(
                therapistAndDateDto.getTherapistId(),
                therapistAndDateDto.getDate()
        ).stream().map(
                DataFormatter::timeToString
        ).toList();
    }

    @PostMapping("/appointments")
    List<AppointmentDto> getAllAppointments(@RequestBody UserDto userDto) {
        User user = userTransformer.toDomain(userDto);
        return appointmentService.getAllAppointments(user).stream().map(
                appointmentTransformer::fromDomain
        ).toList();
    }

    @PutMapping("/reply")
    AppointmentDto setAppointmentReply(@RequestBody Pair<Long, AppointmentReply> appointmentReply) {
        Appointment appointment = appointmentService.setAppointmentReply(appointmentReply);
        return appointmentTransformer.fromDomain(appointment);
    }

    @GetMapping("/therapists/get/{clientId}")
    List<TherapistDto> getAllTherapistsToReview(@PathVariable Long clientId) {
        List<Therapist> therapists = appointmentService.getAllTherapistsToReview(clientId);

        return therapists.stream().map(
                therapistTransformer::fromDomain
        ).toList();
    }
}
