package backend.ports.driven;

import backend.domain.model.Therapist;

import java.util.List;
import java.util.Optional;

public interface TherapistRepository {
    Therapist save(Therapist therapist);
    Optional<Therapist> findById(Long id);
    Optional<Therapist> findByEmail(String email);
    List<Therapist> findAll();
    void deleteById(Long id);
}
