package backend.adapters.driven.transformer;

import backend.domain.model.TherapyType;
import backend.adapters.driven.model.TherapyTypeJpa;
import org.springframework.stereotype.Component;

@Component
public class TherapyTypeTransformer implements Transformer<TherapyType, TherapyTypeJpa> {
    @Override
    public TherapyTypeJpa domainToEntity(TherapyType domain) {
        return new TherapyTypeJpa(
                domain.getId(),
                domain.getShortName(),
                domain.getFullName()
        );
    }

    @Override
    public TherapyType entityToDomain(TherapyTypeJpa entity) {
        return new TherapyType(
                entity.getId(),
                entity.getShortName(),
                entity.getFullName()
        );
    }
}
