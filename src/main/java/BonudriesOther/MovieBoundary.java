package BonudriesOther;

import java.util.HashSet;
import java.util.Set;

public class MovieBoundary {

    private String id;
    private String title;
    private int year;
    private Set<String> genres;
    private String language;
    private int length;
    private Set<String> directors;
    private String organization;

    public MovieBoundary() {
        this.genres = new HashSet<>();
        this.directors = new  HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Set<String> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<String> directors) {
        this.directors = directors;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }


    @Override
    public String toString() {
        return "MovieBoundary{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genres=" + genres +
                ", language='" + language + '\'' +
                ", length=" + length +
                ", directors=" + directors +
                ", organization='" + organization + '\'' +
                '}';
    }
}
