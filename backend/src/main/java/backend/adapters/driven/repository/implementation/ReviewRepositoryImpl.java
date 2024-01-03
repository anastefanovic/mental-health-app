package backend.adapters.driven.repository.implementation;

import backend.adapters.driven.transformer.ReviewTransformer;
import backend.domain.model.Review;
import backend.ports.driven.ReviewRepository;
import backend.adapters.driven.model.ReviewJpa;
import backend.adapters.driven.repository.jpa.ReviewJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReviewRepositoryImpl implements ReviewRepository {
    private final ReviewJpaRepository reviewJpaRepository;
    private final ReviewTransformer reviewTransformer;

    public ReviewRepositoryImpl(
            ReviewJpaRepository reviewJpaRepository,
            ReviewTransformer reviewTransformer
    ) {
        this.reviewJpaRepository = reviewJpaRepository;
        this.reviewTransformer = reviewTransformer;
    }

    @Override
    public Review save(Review review) {
        ReviewJpa reviewJpa = reviewTransformer.domainToEntity(review);
        ReviewJpa savedReview = reviewJpaRepository.save(reviewJpa);
        return reviewTransformer.entityToDomain(savedReview);
    }

    @Override
    public Optional<Review> findById(Long id) {
        Optional<ReviewJpa> reviewJpa = reviewJpaRepository.findById(id);
        return reviewJpa.map(reviewTransformer::entityToDomain);
    }

    @Override
    public void deleteById(Long id) {
        reviewJpaRepository.deleteById(id);
    }

    @Override
    public List<Review> findAllByIsAccepted(boolean isAccepted) {
        List<ReviewJpa> allReviewsJpa = reviewJpaRepository.findAllByIsAccepted(isAccepted);
        return allReviewsJpa.stream().map(reviewTransformer::entityToDomain
        ).toList();
    }
}
