package backend.adapters.driving.transformer;

import backend.domain.model.Appointment;
import backend.adapters.driving.model.AppointmentDto;
import backend.domain.model.enums.AppointmentReply;
import backend.domain.model.enums.SessionType;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AppointmentDtoTransformer implements Transformer<Appointment, AppointmentDto> {
    private final TherapistDtoTransformer therapistTransformer;
    private final ClientDtoTransformer clientTransformer;

    public AppointmentDtoTransformer(
            TherapistDtoTransformer therapistTransformer,
            ClientDtoTransformer clientTransformer
    ) {
        this.therapistTransformer = therapistTransformer;
        this.clientTransformer = clientTransformer;
    }

    @Override
    public AppointmentDto fromDomain(Appointment appointment) {
        return new AppointmentDto(
                appointment.getId(),
                therapistTransformer.fromDomain(appointment.getTherapist()),
                clientTransformer.fromDomain(appointment.getClient()),
                DataFormatter.formatSessionType(appointment.getSessionType()),
                appointment.getReply().toString(),
                DataFormatter.dateToString(appointment.getSessionDate()),
                DataFormatter.timeToString(appointment.getSessionTime())
        );
    }

    @Override
    public Appointment toDomain(AppointmentDto appointmentDto) {
        return new Appointment(
                appointmentDto.getId(),
                therapistTransformer.toDomain(appointmentDto.getTherapist()),
                clientTransformer.toDomain(appointmentDto.getClient()),
                Objects.requireNonNull(SessionType.valueOf(appointmentDto.getSessionType())),
                Objects.requireNonNull(AppointmentReply.valueOf(appointmentDto.getReply())),
                DataFormatter.stringToDate(Objects.requireNonNull(appointmentDto.getSessionDate())),
               DataFormatter.stringToTime( Objects.requireNonNull(appointmentDto.getSessionTime()))
        );
    }
}
