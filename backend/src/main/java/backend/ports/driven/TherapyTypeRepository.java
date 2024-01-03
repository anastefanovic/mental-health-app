package backend.ports.driven;

import backend.domain.model.TherapyType;

import java.util.List;
import java.util.Optional;

public interface TherapyTypeRepository {
    TherapyType save(TherapyType therapyType);
    Optional<TherapyType> findById(Long id);
    List<TherapyType> findAll();
}
