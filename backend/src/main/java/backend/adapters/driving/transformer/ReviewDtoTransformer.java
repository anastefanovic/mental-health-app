package backend.adapters.driving.transformer;

import backend.adapters.driving.model.ReviewDto;
import backend.domain.model.Review;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class ReviewDtoTransformer implements Transformer<Review, ReviewDto> {
    private final TherapistDtoTransformer therapistTransformer;

    public ReviewDtoTransformer (
            TherapistDtoTransformer therapistTransformer
    ) {
        this.therapistTransformer = therapistTransformer;
    }

    @Override
    public ReviewDto fromDomain(Review review) {
        String date = (review.getDate() != null) ? DataFormatter.dateToString(review.getDate()) : null;

        return new ReviewDto(
                review.getId(),
                review.getReviewMessage(),
                date,
                review.isAccepted(),
                therapistTransformer.fromDomain(review.getTherapist())
        );
    }

    @Override
    public Review toDomain(ReviewDto reviewDTO) {
        return new Review(
                reviewDTO.getId(),
                Objects.requireNonNull(reviewDTO.getReviewMessage()),
                DataFormatter.stringToDate(Objects.requireNonNull(reviewDTO.getDate())),
                reviewDTO.isAccepted(),
                therapistTransformer.toDomain(reviewDTO.getTherapist())
        );
    }

}
