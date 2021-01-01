package Model.AllMovies;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("second_title")
    @Expose
    private String secondTitle;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("second_description")
    @Expose
    private String secondDescription;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("director")
    @Expose
    private Object director;
    @SerializedName("language")
    @Expose
    private Object language;
    @SerializedName("user_run_time")
    @Expose
    private String userRunTime;
    @SerializedName("trailer_link")
    @Expose
    private String trailerLink;
    @SerializedName("video_link")
    @Expose
    private String videoLink;
    @SerializedName("generated_link")
    @Expose
    private String generatedLink;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("cast")
    @Expose
    private List<MoviesCast> cast = null;
    @SerializedName("similar_movies")
    @Expose
    private List<SimilarMovie> similarMovies = null;
    @SerializedName("is_favourite")
    @Expose
    private Integer isFavourite;
    @SerializedName("other_language_links")
    @Expose
    private List<OtherLanguageLink> otherLanguageLinks = null;
    @SerializedName("adult")
    @Expose
    private String adult;
    @SerializedName("duration")
    @Expose
    private Object duration;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("article_viewed")
    @Expose
    private Integer articleViewed;
    @SerializedName("subtitles")
    @Expose
    private List<Object> subtitles = null;
    @SerializedName("is_4k")
    @Expose
    private Boolean is4k;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecondDescription() {
        return secondDescription;
    }

    public void setSecondDescription(String secondDescription) {
        this.secondDescription = secondDescription;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Object getDirector() {
        return director;
    }

    public void setDirector(Object director) {
        this.director = director;
    }

    public Object getLanguage() {
        return language;
    }

    public void setLanguage(Object language) {
        this.language = language;
    }

    public String getUserRunTime() {
        return userRunTime;
    }

    public void setUserRunTime(String userRunTime) {
        this.userRunTime = userRunTime;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getGeneratedLink() {
        return generatedLink;
    }

    public void setGeneratedLink(String generatedLink) {
        this.generatedLink = generatedLink;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public List<MoviesCast> getCast() {
        return cast;
    }

    public void setCast(List<MoviesCast> cast) {
        this.cast = cast;
    }

    public List<SimilarMovie> getSimilarMovies() {
        return similarMovies;
    }

    public void setSimilarMovies(List<SimilarMovie> similarMovies) {
        this.similarMovies = similarMovies;
    }

    public Integer getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Integer isFavourite) {
        this.isFavourite = isFavourite;
    }

    public List<OtherLanguageLink> getOtherLanguageLinks() {
        return otherLanguageLinks;
    }

    public void setOtherLanguageLinks(List<OtherLanguageLink> otherLanguageLinks) {
        this.otherLanguageLinks = otherLanguageLinks;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public Object getDuration() {
        return duration;
    }

    public void setDuration(Object duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getArticleViewed() {
        return articleViewed;
    }

    public void setArticleViewed(Integer articleViewed) {
        this.articleViewed = articleViewed;
    }

    public List<Object> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(List<Object> subtitles) {
        this.subtitles = subtitles;
    }

    public Boolean getIs4k() {
        return is4k;
    }

    public void setIs4k(Boolean is4k) {
        this.is4k = is4k;
    }

}