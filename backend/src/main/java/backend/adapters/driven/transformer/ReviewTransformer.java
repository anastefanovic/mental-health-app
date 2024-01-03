package backend.adapters.driven.transformer;

import backend.domain.model.Review;
import backend.domain.model.Therapist;
import backend.adapters.driven.model.ReviewJpa;
import backend.adapters.driven.model.TherapistJpa;
import org.springframework.stereotype.Component;

@Component
public class ReviewTransformer implements Transformer<Review, ReviewJpa> {
    private final TherapistTransformer therapistTransformer;

    public ReviewTransformer(
            TherapistTransformer therapistTransformer
    ) {
        this.therapistTransformer = therapistTransformer;
    }

    @Override
    public ReviewJpa domainToEntity(Review domain) {
        TherapistJpa therapist = therapistTransformer.domainToEntity(domain.getTherapist());

        return new ReviewJpa(
                domain.getId(),
                domain.getReviewMessage(),
                domain.getDate(),
                domain.isAccepted(),
                therapist
        );
    }

    @Override
    public Review entityToDomain(ReviewJpa entity) {
        Therapist therapist = therapistTransformer.entityToDomain(entity.getTherapist());
        return new Review(
                entity.getId(),
                entity.getReviewMessage(),
                entity.getDate(),
                entity.isAccepted(),
                therapist
        );
    }
}
