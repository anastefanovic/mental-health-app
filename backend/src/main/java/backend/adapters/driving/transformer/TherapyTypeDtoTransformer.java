package backend.adapters.driving.transformer;

import backend.adapters.driving.model.TherapyTypeDto;
import backend.domain.model.TherapyType;
import org.springframework.stereotype.Component;

@Component
public class TherapyTypeDtoTransformer implements Transformer<TherapyType, TherapyTypeDto> {
    @Override
    public TherapyTypeDto fromDomain(TherapyType therapyType) {
        return new TherapyTypeDto(
                therapyType.getId(),
                therapyType.getShortName(),
                therapyType.getFullName()
        );
    }

    @Override
    public TherapyType toDomain(TherapyTypeDto therapyTypeDTO) {
        return new TherapyType(
                therapyTypeDTO.getId(),
                therapyTypeDTO.getShortName(),
                therapyTypeDTO.getFullName()
        );
    }
}
