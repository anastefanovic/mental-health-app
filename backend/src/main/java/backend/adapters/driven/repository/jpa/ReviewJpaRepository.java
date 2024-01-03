package backend.adapters.driven.repository.jpa;


import backend.adapters.driven.model.ReviewJpa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewJpaRepository extends CrudRepository<ReviewJpa, Long> {
    List<ReviewJpa> findAllByIsAccepted(boolean isAccepted);
}
