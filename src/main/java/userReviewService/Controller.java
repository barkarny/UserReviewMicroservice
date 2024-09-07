package userReviewService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = ("/review"))
public class Controller {

    private UserReviewService userReviewService;


    public Controller(UserReviewService userReviewService) {
        this.userReviewService = userReviewService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<UserReviewBoundary> create(
            @RequestParam String password ,
            @RequestBody UserReviewBoundary review){
        return this.userReviewService
                .create(review , password);
    }


    @PutMapping(path = ("/{reviewId}"),
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> update(
            @PathVariable("reviewId") String reviewId,
            @RequestParam("password") String password,
            @RequestBody UserReviewBoundary review){
        return this.userReviewService
                .update(reviewId ,review, password) ;
    }

    @GetMapping(
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<UserReviewBoundary> getByCriteria(
            @RequestParam(value = "criteria" ,required = false) String criteria,
            @RequestParam(value = "value" , required = false) String value
    ){
        switch (criteria){

            case "emailAuthor":
                return this.userReviewService.getReviewByEmailAuthor(value);
            case "date":
                return this.userReviewService.getReviewByDate(value);
            case "movieId":
                return this.userReviewService.getReviewByMovieId(value);
                case "score":
                return this.userReviewService.getReviewByRatingScore(value);
            case "shape":
                return this.userReviewService.getReviewByRatingShape(value);


            case null :
                return this.userReviewService.getAll();
            default:
                return  Flux.error(new BadRequest400("bad criteria"));
        }

    }


    @DeleteMapping()
    public Mono<Void> cleanup(){
        return this.userReviewService
                .cleanup();
    }

    @DeleteMapping(path = "{reviewId}")
    public Mono<Void> deleteById(
            @PathVariable("reviewId") String reviewId){
        return this.userReviewService.deleteById(reviewId);
    }
}
