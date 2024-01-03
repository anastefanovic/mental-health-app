package backend.adapters.driven.repository.jpa;

import backend.adapters.driven.model.AdminJpa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AdminJpaRepository extends CrudRepository<AdminJpa, Long> {
    Optional<AdminJpa> findByEmail(String email);
}
