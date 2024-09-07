package userReviewService;

import java.util.HashMap;
import java.util.Map;

public class UserReviewBoundary {

    private String id;
    private String content;
    private String author;
    private String createdTimeStamp;
    private String movieId;
    private  Rating rating;
    private Map<String, Object> deatils;

    public UserReviewBoundary() {}


    public UserReviewBoundary(UserReviewEntity entity){
        this.setId(entity.getId());
        this.setContent(entity.getContent());
        this.setAuthor(entity.getAuthorEmail());
        this.setCreatedTimeStamp(ValidationUtils.dateToString(entity.getCreatedTimeStamp()));
        this.setMovieId(entity.getMovieId());
        Rating rating=new Rating();
        rating.setScore(entity.getScore());
        this.deatils=new HashMap<>();
        if(ValidationUtils.isValidShape(entity.getShape())) {

            rating.setShape(ValidationUtils.stringToShape(entity.getShape()));
        }else {
            rating.setShape((Shape.NONE));

        }
        this.setRating(rating);
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(String createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }


    @Override
    public String toString() {
        return "UserReviewBoundary{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", createdTimeStamp='" + createdTimeStamp + '\'' +
                ", movieId='" + movieId + '\'' +

                '}';
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Map<String, Object> getDeatils() {
        return deatils;
    }

    public void setDeatils(Map<String, Object> deatils) {
        this.deatils = deatils;
    }



}
