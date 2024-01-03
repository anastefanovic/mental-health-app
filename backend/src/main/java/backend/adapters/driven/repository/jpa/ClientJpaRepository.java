package backend.adapters.driven.repository.jpa;


import backend.adapters.driven.model.ClientJpa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientJpaRepository extends CrudRepository<ClientJpa, Long> {
    Optional<ClientJpa> findByEmail(String email);
}
