package backend.domain.service;

import backend.adapters.driving.exception.ResourceNotFoundException;
import backend.domain.model.Review;
import backend.ports.driving.ReviewService;
import backend.ports.driven.ReviewRepository;

import java.time.LocalDate;
import java.util.List;

public class DomainReviewService implements ReviewService {
    private final ReviewRepository reviewRepository;

    public DomainReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review addReview(Review review) {
        review.setDate(LocalDate.now());
        return reviewRepository.save(review);
    }

    @Override
    public Review acceptReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException(
                            "Review with id " + id + " not found"
                    );
                }
        );

        review.setAccepted(true);
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<Review> getAllAcceptedReviews() {
        return reviewRepository.findAllByIsAccepted(true);
    }

    @Override
    public List<Review> getAllUnacceptedReviews() {
        return reviewRepository.findAllByIsAccepted(false);
    }
}
