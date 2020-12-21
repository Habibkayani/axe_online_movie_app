package Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("episode_number")
    @Expose
    private Integer episodeNumber;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("viewed")
    @Expose
    private Integer viewed;
    @SerializedName("language")
    @Expose
    private List<Object> language = null;
    @SerializedName("player_time")
    @Expose
    private List<Object> playerTime = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getViewed() {
        return viewed;
    }

    public void setViewed(Integer viewed) {
        this.viewed = viewed;
    }

    public List<Object> getLanguage() {
        return language;
    }

    public void setLanguage(List<Object> language) {
        this.language = language;
    }

    public List<Object> getPlayerTime() {
        return playerTime;
    }

    public void setPlayerTime(List<Object> playerTime) {
        this.playerTime = playerTime;
    }

}