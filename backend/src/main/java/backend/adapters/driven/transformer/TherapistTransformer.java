package backend.adapters.driven.transformer;

import backend.domain.model.Therapist;
import backend.domain.model.TherapyType;
import backend.adapters.driven.model.TherapistJpa;
import backend.adapters.driven.model.TherapyTypeJpa;
import org.springframework.stereotype.Component;

@Component
public class TherapistTransformer implements Transformer<Therapist, TherapistJpa> {
    private final TherapyTypeTransformer therapyTypeTransformer;

    public TherapistTransformer(
            TherapyTypeTransformer therapyTypeTransformer
    ) {
        this.therapyTypeTransformer = therapyTypeTransformer;
    }

    @Override
    public TherapistJpa domainToEntity(Therapist domain) {
        TherapyTypeJpa therapyType = therapyTypeTransformer.domainToEntity(domain.getTherapyType());

        return new TherapistJpa(
                domain.getId(),
                domain.getEmail(),
                domain.getPassword(),
                domain.getFirstName(),
                domain.getLastName(),
                domain.getPhoneNumber(),
                domain.getLicenceInformation(),
                domain.getSessionPrice(),
                domain.getFirstLogin(),
                domain.getAbout(),
                domain.getAddress(),
                therapyType
        );
    }

    @Override
    public Therapist entityToDomain(TherapistJpa entity) {
        TherapyType therapyType = therapyTypeTransformer.entityToDomain(entity.getTherapyType());

        return new Therapist(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhoneNumber(),
                entity.getAbout(),
                entity.getLicenceInformation(),
                entity.getAddress(),
                entity.getSessionPrice(),
                entity.getFirstLogin(),
                therapyType
        );
    }
}
