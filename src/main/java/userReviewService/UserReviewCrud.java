package userReviewService;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface UserReviewCrud extends ReactiveMongoRepository<UserReviewEntity , String> {


    Flux<UserReviewEntity> findByAuthorEmail(String value);

    Flux<UserReviewEntity> findAllByCreatedTimeStampIsGreaterThanEqual(Date created);


    Flux<UserReviewEntity> findAllByMovieId(String value);
    Flux<UserReviewEntity> findAllById(String value);
    Flux<UserReviewEntity> findAllByScore(double score);
    Flux<UserReviewEntity> findAllByShape(String score);
}
