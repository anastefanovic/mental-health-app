package backend.adapters.driven.repository.jpa;

import backend.adapters.driven.model.TherapistJpa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TherapistJpaRepository extends CrudRepository<TherapistJpa, Long> {
    Optional<TherapistJpa> findByEmail(String email);
    List<TherapistJpa> findAll();
}
