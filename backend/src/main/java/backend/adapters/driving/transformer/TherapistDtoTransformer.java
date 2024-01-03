package backend.adapters.driving.transformer;

import backend.adapters.driving.model.TherapyTypeDto;
import backend.domain.model.Therapist;
import backend.adapters.driving.model.TherapistDto;
import backend.domain.model.TherapyType;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TherapistDtoTransformer implements Transformer<Therapist, TherapistDto> {
    private final TherapyTypeDtoTransformer therapyTypeTransformer;

    public TherapistDtoTransformer(
            TherapyTypeDtoTransformer therapyTypeTransformer
    ) {
        this.therapyTypeTransformer = therapyTypeTransformer;
    }

    @Override
    public TherapistDto fromDomain(Therapist therapist) {
        TherapyTypeDto therapyTypeDTO = therapist.getTherapyType() != null ?
                therapyTypeTransformer.fromDomain(therapist.getTherapyType()) :
                null;

        return new TherapistDto(
                therapist.getId(),
                therapist.getEmail(),
                therapist.getPassword(),
                therapist.getFirstName(),
                therapist.getLastName(),
                therapist.getPhoneNumber(),
                therapist.getAbout(),
                therapist.getLicenceInformation(),
                therapist.getAddress(),
                therapist.getSessionPrice(),
                therapist.getFirstLogin(),
                therapyTypeDTO,
                therapist.isValidationFailed(),
                therapist.getValidationMessage()
        );
    }

    @Override
    public Therapist toDomain(TherapistDto therapistDTO) {
        return new Therapist(
                therapistDTO.getId(),
                Objects.requireNonNull(therapistDTO.getEmail()),
                Objects.requireNonNull(therapistDTO.getPassword()),
                Objects.requireNonNull(therapistDTO.getFirstName()),
                Objects.requireNonNull(therapistDTO.getLastName()),
                Objects.requireNonNull(therapistDTO.getPhoneNumber()),
                therapistDTO.getAbout(),
                Objects.requireNonNull(therapistDTO.getLicenceInformation()),
                therapistDTO.getAddress(),
                therapistDTO.getSessionPrice(),
                therapistDTO.getFirstLogin(),
                therapyTypeTransformer.toDomain(therapistDTO.getTherapyType())
        );
    }

}
