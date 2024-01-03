package backend.ports.driving;

import backend.domain.model.TherapyType;

import java.util.List;

public interface TherapyTypeService {
    TherapyType addTherapyType(TherapyType therapyType);
    List<TherapyType> getAllTherapyTypes();
    TherapyType getTherapyTypeById(Long id);
}
