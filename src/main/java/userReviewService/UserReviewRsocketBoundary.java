package userReviewService;

import java.util.HashMap;
import java.util.Map;

public class UserReviewRsocketBoundary {

    private String id;
    private String content;
    private String author;
    private String createdTimeStamp;
    private String movieId;
    private  Rating rating;
    private String password;

    public UserReviewRsocketBoundary() {}



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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public UserReviewBoundary toBoundary(){
        UserReviewBoundary rv = new UserReviewBoundary();

        rv.setId(this.getId());
        rv.setContent(this.getContent());
        rv.setAuthor(this.getAuthor());
        rv.setMovieId(this.getMovieId());
        rv.setCreatedTimeStamp((this.getCreatedTimeStamp()));
        rv.setRating(this.getRating());
        return rv;
    }
}
