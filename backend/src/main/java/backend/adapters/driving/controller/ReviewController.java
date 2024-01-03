package backend.adapters.driving.controller;

import backend.adapters.driving.model.ReviewDto;
import backend.adapters.driving.transformer.ReviewDtoTransformer;
import backend.domain.model.Review;
import backend.ports.driving.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@CrossOrigin
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewDtoTransformer reviewTransformer;

    public ReviewController(
            ReviewService reviewService,
            ReviewDtoTransformer reviewTransformer
    ) {
        this.reviewService = reviewService;
        this.reviewTransformer = reviewTransformer;
    }

    @PostMapping("/add")
    ReviewDto addReview(@RequestBody ReviewDto reviewDto) {
        Review review = reviewTransformer.toDomain(reviewDto);
        Review returnValue = reviewService.addReview(review);
        return reviewTransformer.fromDomain(returnValue);
    }

    @PutMapping("/accept/{id}")
    public ReviewDto acceptReview(@PathVariable Long id) {
        Review review =  reviewService.acceptReview(id);
        return reviewTransformer.fromDomain(review);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    @GetMapping("/accepted/get")
    public List<ReviewDto> getAllAcceptedReviews() {
        List<Review> acceptedReviews = reviewService.getAllAcceptedReviews();

        return acceptedReviews.stream().map(
                reviewTransformer::fromDomain
        ).toList();
    }

    @GetMapping("/unaccepted/get")
    public List<ReviewDto> getAllUnacceptedReviews() {
        List<Review> unacceptedReviews = reviewService.getAllUnacceptedReviews();

        return unacceptedReviews.stream().map(
                reviewTransformer::fromDomain
        ).toList();
    }
}
