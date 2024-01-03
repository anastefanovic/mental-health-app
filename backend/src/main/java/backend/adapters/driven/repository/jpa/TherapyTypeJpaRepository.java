package backend.adapters.driven.repository.jpa;

import backend.adapters.driven.model.TherapyTypeJpa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TherapyTypeJpaRepository extends CrudRepository<TherapyTypeJpa, Long> {
    List<TherapyTypeJpa> findAll();
}
