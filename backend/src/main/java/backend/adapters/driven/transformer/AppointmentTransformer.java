package backend.adapters.driven.transformer;

import backend.domain.model.Appointment;
import backend.domain.model.Client;
import backend.domain.model.Therapist;
import backend.adapters.driven.model.AppointmentJpa;
import backend.adapters.driven.model.ClientJpa;
import backend.adapters.driven.model.TherapistJpa;
import org.springframework.stereotype.Component;

@Component
public class AppointmentTransformer implements Transformer<Appointment, AppointmentJpa> {
    private final TherapistTransformer therapistTransformer;
    private final ClientTransformer clientTransformer;

    public AppointmentTransformer(
            TherapistTransformer therapistTransformer,
            ClientTransformer clientTransformer
    ) {
        this.therapistTransformer = therapistTransformer;
        this.clientTransformer = clientTransformer;
    }

    @Override
    public AppointmentJpa domainToEntity(Appointment domain) {
        TherapistJpa therapist = therapistTransformer.domainToEntity(domain.getTherapist());
        ClientJpa client = clientTransformer.domainToEntity(domain.getClient());

        return new AppointmentJpa(
                domain.getId(),
                domain.getSessionType(),
                domain.getReply(),
                domain.getSessionDate(),
                domain.getSessionTime(),
                therapist,
                client
        );
    }

    @Override
    public Appointment entityToDomain(AppointmentJpa entity) {
        Therapist therapist = therapistTransformer.entityToDomain(entity.getTherapist());
        Client client = clientTransformer.entityToDomain(entity.getClient());

        return new Appointment(
                entity.getId(),
                therapist,
                client,
                entity.getSessionType(),
                entity.getReply(),
                entity.getSessionDate(),
                entity.getSessionTime()
        );
    }
}
