package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class
Movie implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("is_favourite")
    @Expose
    private Integer isFavourite;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("is_viewed")
    @Expose
    private Integer isViewed;
    @SerializedName("is_premier")
    @Expose
    private String isPremier;
    @SerializedName("last_added_episode")
    @Expose
    public LastAddedEpisode lastAddedEpisode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Integer isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getIsViewed() {
        return isViewed;
    }

    public void setIsViewed(Integer isViewed) {
        this.isViewed = isViewed;
    }

    public String getIsPremier() {
        return isPremier;
    }

    public void setIsPremier(String isPremier) {
        this.isPremier = isPremier;
    }

    public LastAddedEpisode getLastAddedEpisode() {
        return lastAddedEpisode;
    }

    public void setLastAddedEpisode(LastAddedEpisode lastAddedEpisode) {
        this.lastAddedEpisode = lastAddedEpisode;
    }

}

class LastAddedEpisode implements Serializable{

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("episode_number")
    @Expose
    public String episodeNumber;
    @SerializedName("season_number")
    @Expose
    public String seasonNumber;
    @SerializedName("season_title")
    @Expose
    public String seasonTitle;

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

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(String seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getSeasonTitle() {
        return seasonTitle;
    }

    public void setSeasonTitle(String seasonTitle) {
        this.seasonTitle = seasonTitle;
    }

}
