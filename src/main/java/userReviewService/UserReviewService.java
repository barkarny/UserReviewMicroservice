package userReviewService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserReviewService {

    Flux<UserReviewBoundary> create(UserReviewBoundary review, String password);

    Mono<Void> update(String reviewId , UserReviewBoundary review, String password);

    Mono<Void> cleanup();

    Mono<Void> deleteById(String reviewId);

    Flux<UserReviewBoundary> getReviewByEmailAuthor(String value);

    Flux<UserReviewBoundary> getReviewByDate(String value);

    Flux<UserReviewBoundary> getReviewByRatingScore(String value);

    Flux<UserReviewBoundary> getReviewByMovieId(String value);

    Flux<UserReviewBoundary> getAll();
    Mono<UserReviewBoundary> getByID(String id);

    Flux<UserReviewBoundary> getReviewByRatingShape(String value);
}
