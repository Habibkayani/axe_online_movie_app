package Model.AllTvshows;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelTvShowDetail{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("second_title")
    @Expose
    private String secondTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("second_description")
    @Expose
    private String secondDescription;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("trailer")
    @Expose
    private String trailer;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("favourite")
    @Expose
    private Integer favourite;
    @SerializedName("director")
    @Expose
    private String director;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("cast")
    @Expose
    private List<TvShowsCast> cast = null;
    @SerializedName("seasons")
    @Expose
    private List<Season> seasons = null;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getFavourite() {
        return favourite;
    }

    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<TvShowsCast> getCast() {
        return cast;
    }

    public void setCast(List<TvShowsCast> cast) {
        this.cast = cast;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }


}

