package backend.ports.driving;

import backend.domain.model.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(Review review);
    Review acceptReview(Long id);
    void deleteReview(Long id);
    List<Review> getAllAcceptedReviews();
    List<Review> getAllUnacceptedReviews();
}
