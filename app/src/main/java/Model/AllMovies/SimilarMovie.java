package Model.AllMovies;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimilarMovie {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("article_id")
    @Expose
    private Integer articleId;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("adult_video")
    @Expose
    private String adultVideo;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("article_favourite")
    @Expose
    private Integer articleFavourite;
    @SerializedName("language_name")
    @Expose
    private List<String> languageName = null;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("article_viewed")
    @Expose
    private Integer articleViewed;
    @SerializedName("releaseDate")
    @Expose
    private Integer releaseDate;
    @SerializedName("is_4k")
    @Expose
    private Boolean is4k;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAdultVideo() {
        return adultVideo;
    }

    public void setAdultVideo(String adultVideo) {
        this.adultVideo = adultVideo;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getArticleFavourite() {
        return articleFavourite;
    }

    public void setArticleFavourite(Integer articleFavourite) {
        this.articleFavourite = articleFavourite;
    }

    public List<String> getLanguageName() {
        return languageName;
    }

    public void setLanguageName(List<String> languageName) {
        this.languageName = languageName;
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

    public Integer getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Integer releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getIs4k() {
        return is4k;
    }

    public void setIs4k(Boolean is4k) {
        this.is4k = is4k;
    }

}