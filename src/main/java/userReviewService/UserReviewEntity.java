package userReviewService;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Reviews")
public class UserReviewEntity {

    @Id
    private String id;
    private String content;
    private String authorEmail;
    private Date createdTimeStamp;
    private String movieId;
    private double score;
    private String shape;

    public UserReviewEntity() {
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

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Date createdTimeStamp) {
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
        return "UserReviewEntity{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", createdTimeStamp='" + createdTimeStamp + '\'' +
                ", movieId='" + movieId + '\'' +

                '}';
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }


}
