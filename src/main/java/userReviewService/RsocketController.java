package userReviewService;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class RsocketController {

    private UserReviewService userReviewService;

    public RsocketController(UserReviewService userReviewService) {
        this.userReviewService = userReviewService;
    }
    // --stream --route=new-review-request-stream --data="{\"id\":\"1\",\"content\":\"This is a sample content.\",\"author\":\"bar@gmail.com\",\"createdTimeStamp\":\"2024-07-11T12:00:00Z\",\"movieId\":\"123\",\"rating\":{\"score\":4.5,\"shape\":\"STARS\"},\"password\":\"123456\"}" --debug tcp://localhost:7003
    @MessageMapping("new-review-request-stream")
    public Flux<UserReviewBoundary> createUsingReqResp (
            @Payload UserReviewRsocketBoundary review){
            return this.userReviewService.create(review.toBoundary(), review.getPassword());

    }


    // rsc --request --route=update-review-request-respond --data="{\"id\":\"1\",\"content\":\"This is an update\",\"author\":\"bar@gmail.com\",\"createdTimeStamp\":\"2024-07-11T12:00:00Z\",\"movieId\":\"123\",\"rating\":{\"score\":4.5,\"shape\":\"STARS\"},\"password\":\"123456\"}" --debug tcp://localhost:7003
    @MessageMapping("update-review-request-respond")
    public Mono<UserReviewBoundary> updateUsingReqResp (
            @Payload UserReviewRsocketBoundary review
    ){
        return this.userReviewService.update(review.getId() , review.toBoundary() , review.getPassword()
                ).then(this.userReviewService.getByID(review.getId()))
                .switchIfEmpty(Mono.error(new BadRequest400("BAD")));
    }


    //rsc --fnf --route=cleanup-fnf --debug tcp://localhost:7003
    @MessageMapping("cleanup-fnf")
    public Mono<Void> cleanup (){
        return this.userReviewService.cleanup();
    }

    public UserReviewBoundary toBoundary (UserReviewRsocketBoundary review){
        UserReviewBoundary boundary = new UserReviewBoundary();
        boundary.setId(review.getId());
        boundary.setCreatedTimeStamp(review.getCreatedTimeStamp());
        boundary.setAuthor(review.getAuthor());
        boundary.setMovieId(review.getMovieId());
        boundary.setRating(review.getRating());
        boundary.setContent(review.getContent());
        return boundary;
    }

}
