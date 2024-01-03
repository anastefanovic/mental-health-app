package backend.ports.driven;

import backend.domain.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Review save(Review review);
    Optional<Review> findById(Long id);
    void deleteById(Long id);
    List<Review> findAllByIsAccepted(boolean isAccepted);
}
