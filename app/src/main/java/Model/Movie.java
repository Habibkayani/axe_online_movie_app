package Model;

import java.util.List;

public class Movie {


    String title;
    int id;
    String rating;
    String avatar;
    int is_favourite;
    String genre;
    int is_viewed;
    String is_premier;
    List<lastepisode> last_added_episode =null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(int is_favourite) {
        this.is_favourite = is_favourite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getIs_viewed() {
        return is_viewed;
    }

    public void setIs_viewed(int is_viewed) {
        this.is_viewed = is_viewed;
    }

    public String getIs_premier() {
        return is_premier;
    }

    public void setIs_premier(String is_premier) {
        this.is_premier = is_premier;
    }

    public List<lastepisode> getLast_added_episode() {
        return last_added_episode;
    }

    public void setLast_added_episode(List<lastepisode> last_added_episode) {
        this.last_added_episode = last_added_episode;
    }
}
class lastepisode{

    int id;
    String title;
    int episode_number;
    int season_number;
    String season_title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public String getSeason_title() {
        return season_title;
    }

    public void setSeason_title(String season_title) {
        this.season_title = season_title;
    }
}
