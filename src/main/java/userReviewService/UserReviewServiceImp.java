package userReviewService;

import BonudriesOther.MovieBoundary;
import BonudriesOther.PeopleBoundary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service
public class UserReviewServiceImp implements UserReviewService{

    private UserReviewCrud userReviewCrud;
    private WebClient toMovie;
    private WebClient toUser;



    @Value("${remote.service.url:http://localhost:9090/movies}")
    public void setRemoteServiceUrl(String remoteServiceUrl) {
        this.toMovie = WebClient.create(remoteServiceUrl);
    }

    @Value("${remote.service.url:http://localhost:8080/people}")
    public void setWebClientUser(String remoteServiceUrl){
        this.toUser = WebClient.create(remoteServiceUrl);
    }

    public Flux<UserReviewBoundary> askeMovieID(UserReviewBoundary awardsBoundary) {
        return this.toMovie
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("criteria", "id")
                        .queryParam("value", awardsBoundary.getMovieId())
                        .build())
                .retrieve()
                .bodyToFlux(MovieBoundary.class)
                .switchIfEmpty(Flux.empty())
                .flatMap(movieBoundary -> {
                    if(awardsBoundary.getDeatils()==null) {
                        awardsBoundary.setDeatils(new HashMap<>());
                    }
                    awardsBoundary.getDeatils().put("Movie ",movieBoundary);
                    return Mono.just(awardsBoundary);
                })
                .log();
    }

    public  UserReviewServiceImp(UserReviewCrud crud)
    {
        this.userReviewCrud=crud;
    }


    public Flux<UserReviewBoundary> askUser(UserReviewBoundary review ,String password){
        return this.toUser
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path("/{email}")
                                .queryParam("password" , password)
                                .build(review.getAuthor())
                )

                .retrieve()
                .bodyToMono(PeopleBoundary.class)
                .switchIfEmpty(Mono.error(new BadRequest400("Error")))
                .map(peopleBoundary -> {
                    if(review.getDeatils() == null){
                        review.setDeatils(new HashMap<>());
                    }
                    review.getDeatils().put("user " , peopleBoundary);
                    return review;
                }).flux();

    }
    public Mono<UserReviewBoundary> askUserMono(UserReviewBoundary review ,String password){
        return this.toUser
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path("/{email}")
                                .queryParam("password" , password)
                                .build(review.getAuthor())
                )

                .retrieve()
                .bodyToMono(PeopleBoundary.class)
                .switchIfEmpty(Mono.error(new BadRequest400("Error")))
                .map(peopleBoundary -> {
                    if(review.getDeatils() == null){
                        review.setDeatils(new HashMap<>());
                    }
                    review.getDeatils().put("user " , peopleBoundary);
                    return review;
                });

    }

    public Flux<UserReviewBoundary> create(UserReviewBoundary review, String password) {
        if(!ValidationUtils.isEmailFormat(review.getAuthor()))
        {
            return   Flux.error(new BadRequest400("bad author"));
        }


        return this.askeMovieID(review)
                .switchIfEmpty(Flux.error(new BadRequest400("no movieID")))
                .flatMap(reviewBoundary -> {
                    return this.askUser(reviewBoundary , password);
                })
                .flatMap(userReviewBoundary -> {return userReviewCrud.count();})
                .flatMap(aLong -> {
                    review.setCreatedTimeStamp(ValidationUtils.dateToString(new Date()));
                    UserReviewEntity entity;
                    try {
                        entity = toEntity(review);
                    }catch (ParseException parseException)
                    {
                        return  Flux.error(new BadRequest400(parseException.getMessage()));
                    }
                    entity.setId(String.valueOf(aLong + 1));
                    Date date=new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    dateFormat.format(date);
                    entity.setCreatedTimeStamp((new Date()));
                    return this.userReviewCrud.save(entity);
                })
                .map(userReviewEntity -> {
                    UserReviewBoundary userReviewBoundary=new UserReviewBoundary(userReviewEntity);
                    userReviewBoundary.setCreatedTimeStamp(ValidationUtils.dateToString(userReviewEntity.getCreatedTimeStamp()));
                    userReviewBoundary.setDeatils(review.getDeatils());
                    return  userReviewBoundary; })
                .log();
    }





    @Override
    public Mono<Void> update(String reviewId, UserReviewBoundary review, String password) {

        if(reviewId == null || reviewId.isEmpty()){
            return  Mono.error(new BadRequest400("ReviewID not valid"));
        }else    if(!ValidationUtils.isEmailFormat(review.getAuthor()))
        {
            return   Mono.error(new BadRequest400("bad author"));
        }
        else {
            return this.askeMovieID(review)
                    .switchIfEmpty(Mono.error(new BadRequest400("error with movieID")))
                    .flatMap(reviewBoundary -> {
                        return this.askUser(reviewBoundary , password);
                    })
                    .flatMap(userReviewBoundary -> { return this.userReviewCrud
                            .findById(reviewId);})
                    .switchIfEmpty(Mono.error(new BadRequest400("bad rev")))
                    .map(e-> {
                        if (review.getContent() != null) {
                            e.setContent(review.getContent());
                            e.setCreatedTimeStamp(new Date());
                        }
                        return e;
                    })
                    .flatMap(this.userReviewCrud::save )
                    .log()
                    .then();

        }
    }



    @Override
    public Flux<UserReviewBoundary> getReviewByEmailAuthor(String value) {
        if(!ValidationUtils.isEmailFormat(value))
        {
            return  Flux.error(new BadRequest400("bad value"));
        }
        return this.userReviewCrud.findByAuthorEmail(value)
                .switchIfEmpty(Flux.error(new BadRequest400("bad email"))).map(UserReviewBoundary::new).log();
    }


    @Override
    public Flux<UserReviewBoundary> getReviewByDate(String value) {
        Date v=null;
        try {
            v= ValidationUtils.stringToDate(value);
        }catch (ParseException parseException)
        {
            return Flux.error(new BadRequest400("bad date"));
        }
        return this.userReviewCrud.findAllByCreatedTimeStampIsGreaterThanEqual(v).map(UserReviewBoundary::new);
    }

    @Override
    public Flux<UserReviewBoundary> getReviewByRatingScore(String value) {
        if(!ValidationUtils.isDouble(value))
        {
            return  Flux.error(new BadRequest400("bad value"));
        }
        try {
            return this.userReviewCrud.findAllByScore(Double.parseDouble(value)).map(UserReviewBoundary::new).log();

        }catch (NumberFormatException numberFormatException)
        {
            return  Flux.error(new BadRequest400(numberFormatException.getMessage()));

        }
    }


    @Override
    public Flux<UserReviewBoundary> getReviewByMovieId(String value) {
        return this.userReviewCrud.findAllByMovieId(value)
                .flatMap(userReviewEntity -> {
                    return this.askeMovieID(new UserReviewBoundary(userReviewEntity));
                }).switchIfEmpty(Flux.error(new BadRequest400("bad movie")));

    }

    @Override
    public Flux<UserReviewBoundary> getAll() {
        return this.userReviewCrud.findAll()
                .map(UserReviewBoundary::new)
                .log();
    }

    @Override
    public Mono<UserReviewBoundary> getByID(String id) {
        return this.userReviewCrud.findById(id).map(this::toBoundary);
    }

    @Override
    public Flux<UserReviewBoundary> getReviewByRatingShape(String value) {
        if(!ValidationUtils.isValidShape(value))
        {
            return Flux.error(new BadRequest400("bad shape"));
        }
        return this.userReviewCrud.findAllByShape(value).map(UserReviewBoundary::new).log();
    }

    @Override
    public Mono<Void> cleanup() {
        return this.userReviewCrud
                .deleteAll()
                .log();
    }

    @Override
    public Mono<Void> deleteById(String reviewId) {
        return this.userReviewCrud
                .deleteById(reviewId)
                .log();
    }
    public UserReviewEntity toEntity (UserReviewBoundary boundary) throws ParseException {
        UserReviewEntity rv = new UserReviewEntity();

        rv.setId(boundary.getId());
        rv.setContent(boundary.getContent());
        rv.setAuthorEmail(boundary.getAuthor());
        rv.setMovieId(boundary.getMovieId());
        Date d ;
        d=ValidationUtils.stringToDate(boundary.getCreatedTimeStamp());
        rv.setScore(boundary.getRating().getScore());
        rv.setShape(ValidationUtils.shapeToString(boundary.getRating().getShape()));
        rv.setCreatedTimeStamp(d);
        return  rv;
    }

    public UserReviewBoundary toBoundary(UserReviewEntity entity){
        UserReviewBoundary rv = new UserReviewBoundary();

        rv.setId(entity.getId());
        rv.setContent(entity.getContent());
        rv.setAuthor(entity.getAuthorEmail());
        rv.setMovieId(entity.getMovieId());
        rv.setCreatedTimeStamp(ValidationUtils.dateToString(entity.getCreatedTimeStamp()));
        return rv;
    }









}
